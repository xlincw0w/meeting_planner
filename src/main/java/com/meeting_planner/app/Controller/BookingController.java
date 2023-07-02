package com.meeting_planner.app.Controller;

import com.meeting_planner.app.Dto.Booking.in.CreateBookingDto;
import com.meeting_planner.app.Dto.Booking.out.BookingDto;
import com.meeting_planner.app.Dto.Booking.out.BookingSafeDto;
import com.meeting_planner.app.Model.Booking;
import com.meeting_planner.app.Service.BookingService;
import com.meeting_planner.app.Templates.UseResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
@Validated
public class BookingController {

  @Autowired
  private BookingService bookingService;

  @GetMapping
  public ResponseEntity<UseResponse<List<BookingDto>>> fetchRooms(
    @RequestParam Map<String, String> params
  ) {
    UseResponse<List<BookingDto>> response = new UseResponse<>();
    response.setPayload(this.bookingService.fetchRooms());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<UseResponse<BookingDto>> createAnnonce(
    @RequestBody @Valid CreateBookingDto data
  ) throws Exception {
    UseResponse<BookingDto> response = new UseResponse<>();
    response.setPayload(this.bookingService.createBooking(data));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
