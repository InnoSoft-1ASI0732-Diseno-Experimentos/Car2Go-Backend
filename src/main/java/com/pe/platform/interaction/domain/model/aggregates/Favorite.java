package com.pe.platform.interaction.domain.model.aggregates;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Favorite.
 */
@Getter
@Setter
@Entity
@Table(name = "favorites")
public class Favorite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "vehicle_id", nullable = false)
  private Vehicle vehicle;

  @Column(nullable = false)
  private long profileId;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  /**
   * Instantiates a new Favorite.
   */
  public Favorite() {

  }

  /**
   * Instantiates a new Favorite.
   *
   * @param vehicle   the vehicle
   * @param profileId the profile id
   */
  public Favorite(Vehicle vehicle, long profileId) {
    this.vehicle = vehicle;
    this.profileId = profileId;
    this.createdAt = LocalDateTime.now();
  }
}
