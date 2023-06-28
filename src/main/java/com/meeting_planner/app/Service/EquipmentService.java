package com.meeting_planner.app.Service;

import com.meeting_planner.app.Dto.Equipment.CreateEquipmentDto;
import com.meeting_planner.app.Model.Equipment;
import com.meeting_planner.app.Repository.EquipmentRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {

  @Autowired
  private EquipmentRepository equipmentRepository;

  public List<Equipment> fetchRooms() {
    return this.equipmentRepository.findAll();
  }

  public Equipment createRoom(CreateEquipmentDto data) {
    Equipment newEquipment = Equipment
      .builder()
      .name(data.getName())
      .quantity(data.getQuantity())
      .build();

    return this.equipmentRepository.save(newEquipment);
  }

  public Equipment fetchEquipmentById(UUID id) {
    return this.equipmentRepository.findById(id).get();
  }
}
