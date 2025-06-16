package com.pe.platform.profiles.interfaces.rest.transform;

import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.pe.platform.profiles.interfaces.rest.resources.UpdateProfileResource;

/**
 * The type Update profile command from resource.
 */
public class UpdateProfileCommandFromResource {
  /**
   * To command from resource update profile command.
   *
   * @param resource the resource
   * @return the update profile command
   */
  public static UpdateProfileCommand toCommandFromResource(UpdateProfileResource resource) {
    return new UpdateProfileCommand(
        resource.firstName(),
        resource.lastName(),
        resource.email(),
        resource.image(),
        resource.dni(),
        resource.address(),
        resource.phone()
    );
  }
}
