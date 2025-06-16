package com.pe.platform.vehicle.domain.model.queries;

/**
 * The type Get vehicle id by profile id.
 */
public record GetVehicleIdByProfileId(long profileId) {

  /**
   * Profile id long.
   *
   * @return the long
   */
  public long profileId() {
    return profileId;
  }
}
