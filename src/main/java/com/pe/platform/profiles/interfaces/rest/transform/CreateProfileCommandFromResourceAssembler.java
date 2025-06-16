package com.pe.platform.profiles.interfaces.rest.transform;

import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.interfaces.rest.resources.CreateProfileResource;

/**
 * The type Create profile command from resource assembler.
 */
public class CreateProfileCommandFromResourceAssembler {
  /**
   * To command from resource create profile command.
   *
   * @param resource the resource
   * @return the create profile command
   */
  public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
    return new CreateProfileCommand(
        resource.firstName(),
        resource.lastName(),
        resource.email(),
        resource.image(),
        resource.dni(),
        resource.address(),
        resource.phone());
  }
}
