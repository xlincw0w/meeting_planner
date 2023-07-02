package com.meeting_planner.app.Dto.Equipment.out;

import com.meeting_planner.app.Dto.Booking.out.BookingSafeDto;
import com.meeting_planner.app.Dto.Room.out.RoomSafeDto;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentDto {

  private UUID id;
  private String name;
  private int quantity;
  private List<BookingSafeDto> bookings;
  private List<RoomSafeDto> rooms;
}
