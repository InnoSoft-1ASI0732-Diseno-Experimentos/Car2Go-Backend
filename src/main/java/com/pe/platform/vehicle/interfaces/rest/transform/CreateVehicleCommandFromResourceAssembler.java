package com.pe.platform.vehicle.interfaces.rest.transform;

import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.interfaces.rest.resources.CreateVehicleResource;

/**
 * The type Create vehicle command from resource assembler.
 */
public class CreateVehicleCommandFromResourceAssembler {

  /**
   * To command from resource create vehicle command.
   *
   * @param resource the resource
   * @return the create vehicle command
   */
  public static CreateVehicleCommand toCommandFromResource(CreateVehicleResource resource) {
    return new CreateVehicleCommand(
        resource.name(),
        resource.phone(),
        resource.email(),
        resource.brand(),
        resource.model(),
        resource.color(),
        resource.year(),
        resource.price(),
        resource.transmission(),
        resource.engine(),
        resource.mileage(),
        resource.doors(),
        resource.plate(),
        resource.location(),
        resource.description(),
        resource.images(),
        resource.fuel(),
        resource.speed()
    );
  }
}
