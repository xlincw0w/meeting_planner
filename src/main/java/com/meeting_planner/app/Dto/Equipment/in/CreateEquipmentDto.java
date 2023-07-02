package com.meeting_planner.app.Dto.Equipment.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateEquipmentDto {

  @NotBlank
  private String name;

  @NotNull
  @PositiveOrZero
  private Integer quantity;
}
