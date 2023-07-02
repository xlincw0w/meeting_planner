package com.meeting_planner.app.Service;

import com.meeting_planner.app.Dto.Booking.in.CreateBookingDto;
import com.meeting_planner.app.Dto.Booking.out.BookingDto;
import com.meeting_planner.app.Dto.Booking.out.BookingSafeDto;
import com.meeting_planner.app.Dto.Equipment.out.EquipmentSafeDto;
import com.meeting_planner.app.Dto.Room.out.RoomDto;
import com.meeting_planner.app.Dto.Room.out.RoomSafeDto;
import com.meeting_planner.app.Enum.BookingTypeEnum;
import com.meeting_planner.app.Exception.BadRequestException;
import com.meeting_planner.app.Model.Booking;
import com.meeting_planner.app.Model.Equipment;
import com.meeting_planner.app.Model.Room;
import com.meeting_planner.app.Repository.BookingRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  Map<UUID, List<Equipment>> equipmentsPerRoom = new HashMap<>();

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private RoomService roomService;

  @Autowired
  private EquipmentService equipmentService;

  public List<BookingDto> fetchRooms() {
    return this.bookingRepository.findAll()
      .stream()
      .map(booking ->
        BookingDto
          .builder()
          .id(booking.getId())
          .dateFrom(booking.getDateFrom())
          .dateTo(booking.getDateTo())
          .participants(booking.getParticipants())
          .type(booking.getType())
          .room(
            RoomSafeDto
              .builder()
              .id(booking.getRoom().getId())
              .name(booking.getRoom().getName())
              .places(booking.getRoom().getPlaces())
              .build()
          )
          .equipments(
            booking
              .getEquipments()
              .stream()
              .map(equipment -> EquipmentSafeDto.builder().build())
              .toList()
          )
          .build()
      )
      .toList();
  }

  public BookingDto createBooking(CreateBookingDto data) throws Exception {
    List<RoomDto> rooms = this.roomService.fetchRooms();

    this.areValidDates(data);
    List<RoomDto> availableRooms = this.findAvailableRooms(data, rooms);
    List<RoomDto> seventyPercentRooms =
      this.findSenventyPercentFilledRooms(data, availableRooms);
    List<RoomDto> roomsWithEquipments =
      this.findRoomsWithEquipments(data, seventyPercentRooms);
    RoomDto bestRoom = this.findBestRoom(roomsWithEquipments);

    Booking newBooking = Booking
      .builder()
      .dateFrom(data.getDateFrom())
      .dateTo(data.getDateTo())
      .type(data.getType())
      .participants(data.getParticipants())
      .room(Room.builder().id(bestRoom.getId()).build())
      .equipments(this.equipmentsPerRoom.get(bestRoom.getId()))
      .build();

    this.equipmentsPerRoom.clear();
    this.bookingRepository.save(newBooking);

    return BookingDto
      .builder()
      .id(newBooking.getId())
      .dateFrom(newBooking.getDateFrom())
      .dateTo(newBooking.getDateTo())
      .participants(newBooking.getParticipants())
      .room(
        RoomSafeDto
          .builder()
          .id(bestRoom.getId())
          .name(bestRoom.getName())
          .places(bestRoom.getPlaces())
          .build()
      )
      .type(newBooking.getType())
      .build();
  }

  private Boolean areValidDates(CreateBookingDto data) throws Exception {
    if (
      data.getDateFrom().getYear() != data.getDateFrom().getYear() ||
      data.getDateFrom().getDayOfYear() != data.getDateTo().getDayOfYear()
    ) {
      throw new BadRequestException("Dates should have the same day.");
    }

    if (data.getDateFrom().getHour() + 1 > data.getDateTo().getHour()) {
      throw new BadRequestException(
        "[To] date hours should be greater than [From] hour with atleast one hour."
      );
    }

    return true;
  }

  private List<RoomDto> findAvailableRooms(
    CreateBookingDto data,
    List<RoomDto> rooms
  ) throws Exception {
    List<RoomDto> freeRooms = rooms
      .stream()
      .filter(room -> {
        List<BookingSafeDto> roomBookings = room.getBookings();

        AtomicBoolean cleanDate = new AtomicBoolean(true);

        roomBookings
          .stream()
          .forEach(roomBooking -> {
            LocalDateTime bookDateFrom = roomBooking
              .getDateFrom()
              .withHour(roomBooking.getDateFrom().getHour() - 1);
            LocalDateTime bookDateTo = roomBooking
              .getDateTo()
              .withHour(roomBooking.getDateTo().getHour() + 1);

            if (
              (
                data.getDateFrom().isAfter(bookDateFrom) &&
                data.getDateFrom().isBefore(bookDateTo)
              ) ||
              (
                data.getDateTo().isAfter(bookDateFrom) &&
                data.getDateTo().isBefore(bookDateTo)
              ) ||
              (
                data.getDateFrom().equals(roomBooking.getDateFrom()) ||
                data.getDateTo().equals(roomBooking.getDateTo())
              )
            ) {
              cleanDate.set(false);
            }
          });

        return cleanDate.get();
      })
      .toList();

    if (freeRooms.size() == 0) {
      throw new Exception("There is no room available.");
    }

    return freeRooms;
  }

  private List<RoomDto> findSenventyPercentFilledRooms(
    CreateBookingDto data,
    List<RoomDto> rooms
  ) throws Exception {
    List<RoomDto> seventyPercentRooms = rooms
      .stream()
      .filter(room -> {
        Float ratio = (float) data.getParticipants() / (float) room.getPlaces();
        return ratio <= 0.7;
      })
      .toList();

    if (seventyPercentRooms.size() == 0) {
      throw new Exception("There is no room available.");
    }

    return seventyPercentRooms;
  }

  private List<RoomDto> findRoomsWithEquipments(
    CreateBookingDto data,
    List<RoomDto> rooms
  ) throws Exception {
    List<RoomDto> roomsWithEquipments = List.of();

    if (data.getType() == BookingTypeEnum.VC) {
      Equipment screenEquipment =
        this.equipmentService.fetchEquipmentIfAvailable(
            data.getDateFrom(),
            data.getDateTo(),
            "Ecran"
          );

      Equipment webcamEquipment =
        this.equipmentService.fetchEquipmentIfAvailable(
            data.getDateFrom(),
            data.getDateTo(),
            "Webcam"
          );

      Equipment octopusEquipment =
        this.equipmentService.fetchEquipmentIfAvailable(
            data.getDateFrom(),
            data.getDateTo(),
            "Pieuvre"
          );

      roomsWithEquipments =
        rooms
          .stream()
          .filter(room -> {
            List<EquipmentSafeDto> equipments = room.getEquipments();
            List<Equipment> equipmentsIds = new ArrayList<>();

            Boolean screen = equipments
              .stream()
              .anyMatch(value -> value.getName().equals("Ecran"));
            Boolean webcam = equipments
              .stream()
              .anyMatch(value -> value.getName().equals("Webcam"));
            Boolean octopus = equipments
              .stream()
              .anyMatch(value -> value.getName().equals("Pieuvre"));

            if (!screen) {
              if (screenEquipment == null) {
                return false;
              } else {
                equipmentsIds.add(screenEquipment);
              }
            }

            if (!webcam) {
              if (webcamEquipment == null) {
                return false;
              } else {
                equipmentsIds.add(webcamEquipment);
              }
            }

            if (!octopus) {
              if (octopusEquipment == null) {
                return false;
              } else {
                equipmentsIds.add(octopusEquipment);
              }
            }

            this.equipmentsPerRoom.put(room.getId(), equipmentsIds);
            return true;
          })
          .toList();
    }

    if (data.getType() == BookingTypeEnum.SPEC) {
      Equipment whiteboardEquipment =
        this.equipmentService.fetchEquipmentIfAvailable(
            data.getDateFrom(),
            data.getDateTo(),
            "Tableau"
          );

      roomsWithEquipments =
        rooms
          .stream()
          .filter(room -> {
            List<EquipmentSafeDto> equipments = room.getEquipments();
            List<Equipment> equipmentsIds = new ArrayList<>();

            Boolean whiteboard = equipments
              .stream()
              .anyMatch(value -> value.getName().equals("Tableau"));

            if (!whiteboard) {
              if (whiteboardEquipment == null) {
                return false;
              } else {
                equipmentsIds.add(whiteboardEquipment);
              }
            }

            this.equipmentsPerRoom.put(room.getId(), equipmentsIds);
            return true;
          })
          .toList();
    }

    if (data.getType() == BookingTypeEnum.RS) {
      roomsWithEquipments = rooms;
    }

    if (data.getType() == BookingTypeEnum.RC) {
      Equipment whiteboardEquipment =
        this.equipmentService.fetchEquipmentIfAvailable(
            data.getDateFrom(),
            data.getDateTo(),
            "Tableau"
          );

      Equipment screenEquipment =
        this.equipmentService.fetchEquipmentIfAvailable(
            data.getDateFrom(),
            data.getDateTo(),
            "Ecran"
          );

      Equipment octopusEquipment =
        this.equipmentService.fetchEquipmentIfAvailable(
            data.getDateFrom(),
            data.getDateTo(),
            "Pieuvre"
          );

      roomsWithEquipments =
        rooms
          .stream()
          .filter(room -> {
            List<EquipmentSafeDto> equipments = room.getEquipments();
            List<Equipment> equipmentsIds = new ArrayList<>();

            Boolean screen = equipments
              .stream()
              .anyMatch(value -> value.getName().equals("Ecran"));
            Boolean whiteboard = equipments
              .stream()
              .anyMatch(value -> value.getName().equals("Tableau"));
            Boolean octopus = equipments
              .stream()
              .anyMatch(value -> value.getName().equals("Pieuvre"));

            if (!screen) {
              if (screenEquipment == null) {
                return false;
              } else {
                equipmentsIds.add(screenEquipment);
              }
            }

            if (!whiteboard) {
              if (whiteboardEquipment == null) {
                return false;
              } else {
                equipmentsIds.add(whiteboardEquipment);
              }
            }

            if (!octopus) {
              if (octopusEquipment == null) {
                return false;
              } else {
                equipmentsIds.add(octopusEquipment);
              }
            }

            this.equipmentsPerRoom.put(room.getId(), equipmentsIds);
            return true;
          })
          .toList();
    }

    if (roomsWithEquipments.size() == 0) {
      throw new Exception("There is no room available.");
    }

    return roomsWithEquipments;
  }

  private RoomDto findBestRoom(List<RoomDto> rooms) throws Exception {
    RoomDto bestRoom = Collections.min(
      rooms,
      Comparator.comparingInt(RoomDto::getPlaces)
    );

    return bestRoom;
  }
}
