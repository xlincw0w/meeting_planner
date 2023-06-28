package com.meeting_planner.app.Service;

import com.meeting_planner.app.Dto.Room.AssignEquipmentDto;
import com.meeting_planner.app.Dto.Room.CreateRoomDto;
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

  public List<Room> fetchRooms() {
    return this.roomRepository.findAll();
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

  public Room assignEquipment(AssignEquipmentDto data) {
    Optional<Room> checkRoom = this.roomRepository.findById(data.getRoomId());

    if (checkRoom.isEmpty()) throw new EntityNotFoundException(
      "Room doesn't exist."
    );

    Room room = this.roomRepository.findById(data.getRoomId()).get();
    Equipment equipment =
      this.equipmentService.fetchEquipmentById(data.getEquipmentId());

    room.getEquipments().add(equipment);

    return this.roomRepository.save(room);
  }
}
