package com.pe.platform.vehicle.domain.model.queries;

/**
 * The type Put vehicle by id.
 */
public record PutVehicleById(Integer id) {
  /**
   * Instantiates a new Put vehicle by id.
   *
   * @param id the id
   */
  public PutVehicleById {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("ID must be greater than zero");
    }
  }
}
