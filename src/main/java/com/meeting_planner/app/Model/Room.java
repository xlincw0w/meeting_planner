package com.meeting_planner.app.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
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
public class Room {

  @Id
  @GeneratedValue
  private UUID id;

  @NotNull
  @Column(unique = true)
  private String name;

  @NotNull
  private int places;

  @OneToMany(mappedBy = "room")
  private List<Booking> bookings;

  @ManyToMany
  @JoinTable(
    name = "room_equipment",
    joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
      name = "equipment_id",
      referencedColumnName = "id"
    )
  )
  private Set<Equipment> equipments;
}
