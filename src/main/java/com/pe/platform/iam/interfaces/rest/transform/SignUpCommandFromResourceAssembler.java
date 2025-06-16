package com.pe.platform.iam.interfaces.rest.transform;


import com.pe.platform.iam.domain.model.commands.SignUpCommand;
import com.pe.platform.iam.domain.model.entities.Role;
import com.pe.platform.iam.interfaces.rest.resources.SignUpResource;
import java.util.ArrayList;

/**
 * The type Sign up command from resource assembler.
 */
public class SignUpCommandFromResourceAssembler {
  /**
   * To command from resource sign up command.
   *
   * @param resource the resource
   * @return the sign up command
   */
  public static SignUpCommand toCommandFromResource(SignUpResource resource) {
    var roles = resource.roles() != null ?
        resource.roles().stream().map(name -> Role.toRoleFromName(name)).toList() :
        new ArrayList<Role>();
    return new SignUpCommand(resource.username(), resource.password(), roles);
  }
}