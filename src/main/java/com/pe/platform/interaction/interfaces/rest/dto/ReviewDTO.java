package com.pe.platform.interaction.interfaces.rest.dto;

import com.pe.platform.interaction.domain.model.aggregates.Review;
import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.interfaces.rest.dto.VehicleSummaryDTO;
import java.time.LocalDateTime;

/**
 * The type Review dto.
 */
public class ReviewDTO {
  private final Long id;
  private final String reviewedBy;
  private final String notes;
  private final LocalDateTime reviewDate;

  private VehicleSummaryDTO vehicle;

  /**
   * Instantiates a new Review dto.
   *
   * @param review the review
   */
  public ReviewDTO(Review review) {
    this.id = review.getId();
    this.reviewedBy = review.getReviewedBy();
    this.notes = review.getNotes();
    this.reviewDate = review.getReviewDate();

    Vehicle vehicle = review.getVehicle();
    if (vehicle != null) {
      this.vehicle = new VehicleSummaryDTO(
          vehicle.getId(),
          vehicle.getName(),
          vehicle.getBrand(),
          vehicle.getModel(),
          vehicle.getYear(),
          vehicle.getPrice(),
          vehicle.getTransmission(),
          vehicle.getEngine(),
          vehicle.getStatus().toString()
      );
    }
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets reviewed by.
   *
   * @return the reviewed by
   */
  public String getReviewedBy() {
    return reviewedBy;
  }

  /**
   * Gets notes.
   *
   * @return the notes
   */
  public String getNotes() {
    return notes;
  }

  /**
   * Gets review date.
   *
   * @return the review date
   */
  public LocalDateTime getReviewDate() {
    return reviewDate;
  }

  /**
   * Gets vehicle.
   *
   * @return the vehicle
   */
  public VehicleSummaryDTO getVehicle() {
    return vehicle;
  }
}
