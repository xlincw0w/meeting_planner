package com.meeting_planner.app.Repository;

import com.meeting_planner.app.Model.Equipment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EquipmentRepository
  extends JpaRepository<Equipment, UUID>, JpaSpecificationExecutor<Equipment> {}
