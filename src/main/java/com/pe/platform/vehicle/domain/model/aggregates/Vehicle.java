package com.pe.platform.vehicle.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pe.platform.interaction.domain.model.aggregates.Review;
import com.pe.platform.vehicle.common.converters.ListToStringConverter;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;
import com.pe.platform.vehicle.domain.model.valueobjects.vehicleStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The type Vehicle.
 */
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Vehicle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String brand;

  @Column(nullable = false)
  private String model;

  @Column(nullable = false)
  private String color;

  @Column(nullable = false)
  private String year;

  @Column(nullable = false)
  private double price;

  @Column(nullable = false)
  private String transmission;

  @Column(nullable = false)
  private String engine;

  @Column(nullable = false)
  private double mileage;

  @Column(nullable = false)
  private String doors;

  @Column(nullable = false)
  private String plate;

  @Column(nullable = false)
  private String location;

  @Column(nullable = false)
  private String description;

  @Convert(converter = ListToStringConverter.class)
  @Column(name = "images", columnDefinition = "LONGTEXT")
  private List<String> images;

  @Column(nullable = false)
  private String fuel;

  @Column(nullable = false)
  private int speed;

  @Column(nullable = false)
  private long profileId;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private vehicleStatus status;


  @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Review> reviews;

  /**
   * Instantiates a new Vehicle.
   */
  public Vehicle() {
  }

  /**
   * Instantiates a new Vehicle.
   *
   * @param command the command
   */
  public Vehicle(CreateVehicleCommand command) {
    this.name = command.name();
    this.phone = command.phone();
    this.email = command.email();
    this.brand = command.brand();
    this.model = command.model();
    this.color = command.color();
    this.year = command.year();
    this.price = command.price();
    this.transmission = command.transmission();
    this.engine = command.engine();
    this.mileage = command.mileage();
    this.doors = command.doors();
    this.plate = command.plate();
    this.location = command.location();
    this.description = command.description();
    this.images = command.images();
    this.fuel = command.fuel();
    this.speed = command.speed();
    this.status = vehicleStatus.PENDING;
  }

  /**
   * Update vehicle info vehicle.
   *
   * @param command the command
   * @return the vehicle
   */
  public Vehicle updateVehicleInfo(UpdateVehicleCommand command) {
    this.name = command.name();
    this.phone = command.phone();
    this.email = command.email();
    this.brand = command.brand();
    this.model = command.model();
    this.color = command.color();
    this.year = command.year();
    this.price = command.price();
    this.transmission = command.transmission();
    this.engine = command.engine();
    this.mileage = command.mileage();
    this.doors = command.doors();
    this.plate = command.plate();
    this.location = command.location();
    this.description = command.description();
    this.images = command.images();
    this.fuel = command.fuel();
    this.speed = command.speed();

    if (command.status() != null) {
      this.status = command.status();
    }

    return this;
  }

  /**
   * Sets profile id.
   *
   * @param profileId the profile id
   */
  public void setProfileId(long profileId) {
    this.profileId = profileId;
  }

  /**
   * Gets user id.
   *
   * @return the user id
   */
  public long getUserId() {
    return profileId;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }
}
