package com.meeting_planner.app.Service;

import com.meeting_planner.app.Dto.Booking.out.BookingSafeDto;
import com.meeting_planner.app.Dto.Equipment.out.EquipmentSafeDto;
import com.meeting_planner.app.Dto.Room.in.AssignEquipmentDto;
import com.meeting_planner.app.Dto.Room.in.CreateRoomDto;
import com.meeting_planner.app.Dto.Room.out.RoomDto;
import com.meeting_planner.app.Model.Equipment;
import com.meeting_planner.app.Model.Room;
import com.meeting_planner.app.Repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private EquipmentService equipmentService;

  public List<RoomDto> fetchRooms() {
    return this.roomRepository.findAll()
      .stream()
      .map(room ->
        RoomDto
          .builder()
          .id(room.getId())
          .name(room.getName())
          .places(room.getPlaces())
          .bookings(
            room
              .getBookings()
              .stream()
              .map(row ->
                BookingSafeDto
                  .builder()
                  .id(row.getId())
                  .dateFrom(row.getDateFrom())
                  .dateTo(row.getDateTo())
                  .participants(row.getParticipants())
                  .type(row.getType())
                  .build()
              )
              .toList()
          )
          .equipments(
            room
              .getEquipments()
              .stream()
              .map(equipment ->
                EquipmentSafeDto
                  .builder()
                  .id(equipment.getId())
                  .name(equipment.getName())
                  .quantity(equipment.getQuantity())
                  .build()
              )
              .toList()
          )
          .build()
      )
      .toList();
  }

  public Room fetchRoomById(UUID id) {
    return this.roomRepository.findById(id).get();
  }

  public Room createRoom(CreateRoomDto data) {
    Room newRoom = Room
      .builder()
      .name(data.getName())
      .places(data.getPlaces())
      .build();

    return this.roomRepository.save(newRoom);
  }

  public RoomDto assignEquipment(AssignEquipmentDto data) {
    Optional<Room> checkRoom = this.roomRepository.findById(data.getRoomId());

    if (checkRoom.isEmpty()) throw new EntityNotFoundException(
      "Room doesn't exist."
    );

    Room room = this.roomRepository.findById(data.getRoomId()).get();
    Equipment equipment =
      this.equipmentService.fetchEquipmentById(data.getEquipmentId());

    room.getEquipments().add(equipment);

    Room result = this.roomRepository.save(room);

    return RoomDto
      .builder()
      .id(result.getId())
      .name(result.getName())
      .places(result.getPlaces())
      .equipments(
        result
          .getEquipments()
          .stream()
          .map(row ->
            EquipmentSafeDto
              .builder()
              .id(row.getId())
              .name(row.getName())
              .quantity(row.getQuantity())
              .build()
          )
          .toList()
      )
      .build();
  }
}
