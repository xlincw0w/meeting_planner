package com.meeting_planner.app.Dto.Room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateRoomDto {

  @NotBlank
  private String name;

  @NotNull
  @Positive
  private Integer places;
}
