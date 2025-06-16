package com.pe.platform.iam.interfaces.rest.transform;

import com.pe.platform.iam.domain.model.commands.SignInCommand;
import com.pe.platform.iam.interfaces.rest.resources.SignInResource;

/**
 * The type Sign in command from resource assembler.
 */
public class SignInCommandFromResourceAssembler {
  /**
   * To command from resource sign in command.
   *
   * @param signInResource the sign in resource
   * @return the sign in command
   */
  public static SignInCommand toCommandFromResource(SignInResource signInResource) {
    return new SignInCommand(signInResource.username(), signInResource.password());
  }
}