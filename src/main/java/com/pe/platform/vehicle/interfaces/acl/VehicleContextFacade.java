package com.pe.platform.vehicle.interfaces.acl;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleIdByProfileId;
import com.pe.platform.vehicle.domain.services.VehicleQueryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * The type Vehicle context facade.
 */
@Service
public class VehicleContextFacade {

  private final VehicleQueryService vehicleQueryService;

  /**
   * Instantiates a new Vehicle context facade.
   *
   * @param vehicleQueryService the vehicle query service
   */
  public VehicleContextFacade(VehicleQueryService vehicleQueryService) {
    this.vehicleQueryService = vehicleQueryService;
  }

  /**
   * Find by profile id list.
   *
   * @param userId the user id
   * @return the list
   */
  public List<Long> findByProfileId(long userId) {
    List<Vehicle> vehicles = vehicleQueryService.handle(new GetVehicleIdByProfileId(userId));

    return vehicles.stream()
        .map(vehicle -> Long.valueOf(vehicle.getId()))
        .collect(Collectors.toList());
  }
}
