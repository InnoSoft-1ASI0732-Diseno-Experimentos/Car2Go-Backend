package com.pe.platform.payment.domain.model.commands;

/**
 * The type Create transaction command.
 */
public record CreateTransactionCommand(Long vehicleId) {
  /**
   * Instantiates a new Create transaction command.
   *
   * @param vehicleId the vehicle id
   */
  public CreateTransactionCommand {
    if (vehicleId == null || vehicleId <= 0) {
      throw new IllegalArgumentException("VehicleId cannot be null");
    }
  }
}
