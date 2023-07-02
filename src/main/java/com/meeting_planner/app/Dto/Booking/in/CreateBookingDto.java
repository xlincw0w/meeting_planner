package com.meeting_planner.app.Dto.Booking.in;

import com.meeting_planner.app.Enum.BookingTypeEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateBookingDto {

  @NotNull
  @Future
  private LocalDateTime dateFrom;

  @NotNull
  @Future
  private LocalDateTime dateTo;

  @NotNull
  @Positive
  private Integer participants;

  @NotNull
  private BookingTypeEnum type;
}
