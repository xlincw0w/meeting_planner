package com.meeting_planner.app.Dto.Room;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateRoomDto {

  @Nullable
  @Size(min = 1)
  private String name;

  @Nullable
  @PositiveOrZero
  private Integer places;
}
