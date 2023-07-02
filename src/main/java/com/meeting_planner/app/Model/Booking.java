package com.meeting_planner.app.Model;

import com.meeting_planner.app.Enum.BookingTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Booking {

  @Id
  @GeneratedValue
  private UUID id;

  @NotNull
  private LocalDateTime dateFrom;

  @NotNull
  private LocalDateTime dateTo;

  @NotNull
  private int participants;

  @NotNull
  private BookingTypeEnum type;

  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;

  @ManyToMany
  @JoinTable(
    name = "booking_equipment",
    joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
      name = "equipment_id",
      referencedColumnName = "id"
    )
  )
  private List<Equipment> equipments;
}
