package com.meeting_planner.app.Service;

import com.meeting_planner.app.Model.Booking;
import com.meeting_planner.app.Repository.BookingRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

  public List<Booking> fetchRooms() {
    return this.bookingRepository.findAll();
  }
}
