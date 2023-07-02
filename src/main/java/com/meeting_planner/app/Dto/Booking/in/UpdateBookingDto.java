package com.meeting_planner.app.Dto.Booking.in;

import com.meeting_planner.app.Enum.BookingTypeEnum;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UpdateBookingDto {

  @Nullable
  private LocalDateTime dateFrom;

  @Nullable
  private LocalDateTime dateTo;

  @Nullable
  @Positive
  private Integer participants;

  @Nullable
  private BookingTypeEnum type;
}
