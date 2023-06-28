package com.meeting_planner.app.Dto.Booking;

import com.meeting_planner.app.Enum.BookingTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateBookingDto {

  @NotBlank
  private LocalDateTime fromDate;

  @NotBlank
  private LocalDateTime toDate;

  @NotNull
  @Positive
  private Integer participants;

  @NotNull
  private BookingTypeEnum type;
}
