package com.meeting_planner.app.Dto.Room;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class AssignEquipmentDto {

  @NotNull
  private UUID roomId;

  @NotNull
  private UUID equipmentId;
}
