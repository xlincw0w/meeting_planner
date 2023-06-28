package com.meeting_planner.app.Repository;

import com.meeting_planner.app.Model.Booking;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookingRepository
  extends JpaRepository<Booking, UUID>, JpaSpecificationExecutor<Booking> {}
