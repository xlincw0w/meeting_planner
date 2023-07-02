package com.meeting_planner.app.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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
public class Equipment {

  @Id
  @GeneratedValue
  private UUID id;

  @NotNull
  @Column(unique = true)
  private String name;

  @NotNull
  private int quantity;

  @ManyToMany(mappedBy = "equipments")
  private List<Booking> bookings;

  @ManyToMany(mappedBy = "equipments")
  private List<Room> rooms;
}
