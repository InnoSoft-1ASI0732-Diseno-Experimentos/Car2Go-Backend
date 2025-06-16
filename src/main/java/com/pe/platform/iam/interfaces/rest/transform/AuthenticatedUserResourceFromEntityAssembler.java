package com.pe.platform.iam.interfaces.rest.transform;

import com.pe.platform.iam.domain.model.aggregates.User;
import com.pe.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * The type Authenticated user resource from entity assembler.
 */
public class AuthenticatedUserResourceFromEntityAssembler {
  /**
   * To resource from entity authenticated user resource.
   *
   * @param user  the user
   * @param token the token
   * @return the authenticated user resource
   */
  public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
    return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
  }
}