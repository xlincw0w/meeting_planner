package com.meeting_planner.app.Dto.Equipment.out;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentSafeDto {

  private UUID id;
  private String name;
  private int quantity;
}
