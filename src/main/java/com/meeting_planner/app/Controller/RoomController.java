package com.meeting_planner.app.Controller;

import com.meeting_planner.app.Dto.Room.AssignEquipmentDto;
import com.meeting_planner.app.Dto.Room.CreateRoomDto;
import com.meeting_planner.app.Model.Room;
import com.meeting_planner.app.Service.RoomService;
import com.meeting_planner.app.Templates.UseResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
@Validated
public class RoomController {

  @Autowired
  private RoomService roomService;

  @GetMapping
  public ResponseEntity<UseResponse<List<Room>>> fetchRooms(
    @RequestParam Map<String, String> params
  ) {
    UseResponse<List<Room>> response = new UseResponse<>();
    response.setPayload(this.roomService.fetchRooms());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UseResponse<Room>> fetchRoom(
    @PathVariable("id") UUID id
  ) {
    UseResponse<Room> response = new UseResponse<>();
    response.setPayload(this.roomService.fetchRoomById(id));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<UseResponse<Room>> createAnnonce(
    @RequestBody @Valid CreateRoomDto data
  ) {
    UseResponse<Room> response = new UseResponse<>();
    response.setPayload(this.roomService.createRoom(data));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/assign-equipment")
  public ResponseEntity<UseResponse<Room>> assignEquipment(
    @RequestBody @Valid AssignEquipmentDto data
  ) {
    UseResponse<Room> response = new UseResponse<>();
    response.setPayload(this.roomService.assignEquipment(data));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
