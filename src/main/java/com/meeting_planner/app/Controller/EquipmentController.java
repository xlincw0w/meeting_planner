package com.meeting_planner.app.Controller;

import com.meeting_planner.app.Dto.Equipment.CreateEquipmentDto;
import com.meeting_planner.app.Model.Equipment;
import com.meeting_planner.app.Service.EquipmentService;
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
@RequestMapping("/api/equipment")
@Validated
public class EquipmentController {

  @Autowired
  private EquipmentService equipmentService;

  @GetMapping
  public ResponseEntity<UseResponse<List<Equipment>>> fetchRooms(
    @RequestParam Map<String, String> params
  ) {
    UseResponse<List<Equipment>> response = new UseResponse<>();
    response.setPayload(this.equipmentService.fetchRooms());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<UseResponse<Equipment>> createAnnonce(
    @RequestBody @Valid CreateEquipmentDto data
  ) {
    UseResponse<Equipment> response = new UseResponse<>();
    response.setPayload(this.equipmentService.createRoom(data));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
