package com.pe.platform.interaction.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * The type Review.
 */
@Entity
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "vehicle_id", nullable = false)
  @JsonIgnore
  private Vehicle vehicle;

  @Column(nullable = false)
  private String reviewedBy;

  @Column(nullable = false)
  private String notes;

  @Column(nullable = false)
  private LocalDateTime reviewDate;

  /**
   * Instantiates a new Review.
   */
  protected Review() {
  }

  /**
   * Instantiates a new Review.
   *
   * @param vehicle    the vehicle
   * @param reviewedBy the reviewed by
   * @param notes      the notes
   */
  public Review(Vehicle vehicle, String reviewedBy, String notes) {
    this.vehicle = vehicle;
    this.reviewedBy = reviewedBy;
    this.notes = notes;
    this.reviewDate = LocalDateTime.now();
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
   * Gets vehicle.
   *
   * @return the vehicle
   */
  public Vehicle getVehicle() {
    return vehicle;
  }

  /**
   * Sets vehicle.
   *
   * @param vehicle the vehicle
   */
  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
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
   * Sets reviewed by.
   *
   * @param reviewedBy the reviewed by
   */
  public void setReviewedBy(String reviewedBy) {
    this.reviewedBy = reviewedBy;
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
   * Sets notes.
   *
   * @param notes the notes
   */
  public void setNotes(String notes) {
    this.notes = notes;
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
   * Sets review date.
   *
   * @param reviewDate the review date
   */
  public void setReviewDate(LocalDateTime reviewDate) {
    this.reviewDate = reviewDate;
  }


}
