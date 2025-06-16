package com.pe.platform.interaction.interfaces.rest.dto;  // Ajusta este paquete según la ubicación que elijas.

/**
 * The type Create review request.
 */
public class CreateReviewRequest {
  private int vehicleId;
  private String notes;
  private boolean isApproved;

  /**
   * Instantiates a new Create review request.
   */
// Constructor sin parámetros
  public CreateReviewRequest() {
  }

  /**
   * Gets vehicle id.
   *
   * @return the vehicle id
   */
// Getters y Setters
  public int getVehicleId() {
    return vehicleId;
  }

  /**
   * Sets vehicle id.
   *
   * @param vehicleId the vehicle id
   */
  public void setVehicleId(int vehicleId) {
    this.vehicleId = vehicleId;
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
   * Is approved boolean.
   *
   * @return the boolean
   */
  public boolean isApproved() {
    return isApproved;
  }

  /**
   * Sets approved.
   *
   * @param approved the approved
   */
  public void setApproved(boolean approved) {
    isApproved = approved;
  }
}
