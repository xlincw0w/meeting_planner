package com.meeting_planner.app.Repository;

import com.meeting_planner.app.Model.Room;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoomRepository
  extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {}
