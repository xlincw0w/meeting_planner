package com.meeting_planner.app.Dto.Room.out;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomSafeDto {

  private UUID id;
  private String name;
  private int places;
}
