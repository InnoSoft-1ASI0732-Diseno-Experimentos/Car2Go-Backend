package com.pe.platform.vehicle.domain.model.queries;

/**
 * The type Get vehicle by id query.
 */
public record GetVehicleByIdQuery(Integer id) {
  /**
   * Instantiates a new Get vehicle by id query.
   *
   * @param id the id
   */
  public GetVehicleByIdQuery {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("ID must be greater than zero");
    }
  }
}
