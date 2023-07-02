package com.meeting_planner.app.Dto.Room.out;

import com.meeting_planner.app.Dto.Booking.out.BookingSafeDto;
import com.meeting_planner.app.Dto.Equipment.out.EquipmentSafeDto;
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
public class RoomDto {

  private UUID id;
  private String name;
  private int places;
  private List<BookingSafeDto> bookings;
  private List<EquipmentSafeDto> equipments;
}
