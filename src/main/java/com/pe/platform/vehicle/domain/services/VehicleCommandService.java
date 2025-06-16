package com.pe.platform.vehicle.domain.services;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;
import java.util.Optional;

/**
 * The interface Vehicle command service.
 */
public interface VehicleCommandService {

  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<Vehicle> handle(CreateVehicleCommand command);

  /**
   * Handle optional.
   *
   * @param command   the command
   * @param vehicleId the vehicle id
   * @return the optional
   */
  Optional<Vehicle> handle(UpdateVehicleCommand command, int vehicleId);

  /**
   * Delete vehicle.
   *
   * @param vehicleId the vehicle id
   * @param userId    the user id
   */
  void deleteVehicle(int vehicleId, long userId);

  /**
   * Find by id optional.
   *
   * @param vehicleId the vehicle id
   * @return the optional
   */
  Optional<Vehicle> findById(int vehicleId);
}
