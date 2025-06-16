package com.pe.platform.vehicle.domain.services;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByLocationQuery;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByProfileId;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleQuery;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleByIdQuery;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleIdByProfileId;
import com.pe.platform.vehicle.domain.model.queries.PutVehicleById;
import java.util.List;
import java.util.Optional;

/**
 * The interface Vehicle query service.
 */
public interface VehicleQueryService {
  /**
   * Handle optional.
   *
   * @param query the query
   * @return the optional
   */
  Optional<Vehicle> handle(GetVehicleByIdQuery query);

  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<Vehicle> handle(GetAllVehicleByLocationQuery query);

  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<Vehicle> handle(GetAllVehicleQuery query);

  /**
   * Handle optional.
   *
   * @param query the query
   * @return the optional
   */
  Optional<Vehicle> handle(PutVehicleById query);

  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<Vehicle> handle(GetAllVehicleByProfileId query);

  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<Vehicle> handle(GetVehicleIdByProfileId query);
}
