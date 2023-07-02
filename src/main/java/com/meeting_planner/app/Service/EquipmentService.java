package com.meeting_planner.app.Service;

import com.meeting_planner.app.Dto.Equipment.in.CreateEquipmentDto;
import com.meeting_planner.app.Dto.Equipment.out.EquipmentDto;
import com.meeting_planner.app.Dto.Equipment.out.EquipmentSafeDto;
import com.meeting_planner.app.Model.Equipment;
import com.meeting_planner.app.Repository.EquipmentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {

  @Autowired
  private EquipmentRepository equipmentRepository;

  public List<EquipmentSafeDto> fetchRooms() {
    return this.equipmentRepository.findAll()
      .stream()
      .map(equipment ->
        EquipmentSafeDto
          .builder()
          .id(equipment.getId())
          .name(equipment.getName())
          .quantity(equipment.getQuantity())
          .build()
      )
      .toList();
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

  public Equipment fetchEquipmentIfAvailable(
    LocalDateTime dateFrom,
    LocalDateTime dateTo,
    String name
  ) {
    Equipment equipment = this.equipmentRepository.findOneByName(name);

    if (equipment == null) return null;

    int used = equipment
      .getBookings()
      .stream()
      .filter(booking -> {
        return (
          (
            dateFrom.isAfter(booking.getDateFrom()) &&
            dateFrom.isBefore(booking.getDateTo())
          ) ||
          (
            dateTo.isAfter(booking.getDateFrom()) &&
            dateTo.isBefore(booking.getDateTo())
          ) ||
          (
            dateFrom.equals(booking.getDateFrom()) ||
            dateTo.equals(booking.getDateTo())
          )
        );
      })
      .toList()
      .size();

    return equipment.getQuantity() - used > 0 ? equipment : null;
  }
}
