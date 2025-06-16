package com.pe.platform.vehicle.domain.model.queries;

/**
 * The type Get all vehicle by location query.
 */
public record GetAllVehicleByLocationQuery(String location) {
  /**
   * Instantiates a new Get all vehicle by location query.
   *
   * @param location the location
   */
  public GetAllVehicleByLocationQuery {
    if (location == null || location.isBlank()) {
      throw new IllegalArgumentException("Location must not be null or empty");
    }
  }
}
