package com.meeting_planner.app.Dto.Booking.out;

import com.meeting_planner.app.Enum.BookingTypeEnum;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingSafeDto {

  private UUID id;
  private LocalDateTime dateFrom;
  private LocalDateTime dateTo;
  private int participants;
  private BookingTypeEnum type;
}
