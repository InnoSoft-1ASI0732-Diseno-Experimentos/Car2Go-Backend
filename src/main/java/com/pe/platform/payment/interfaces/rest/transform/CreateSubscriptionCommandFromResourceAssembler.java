package com.pe.platform.payment.interfaces.rest.transform;

import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.payment.domain.model.commands.CreateSubscriptionCommand;
import com.pe.platform.payment.interfaces.rest.resources.CreateSubscriptionResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The type Create subscription command from resource assembler.
 */
public class CreateSubscriptionCommandFromResourceAssembler {
  /**
   * To command from resource create subscription command.
   *
   * @param resource the resource
   * @return the create subscription command
   */
  public static CreateSubscriptionCommand toCommandFromResource(
      CreateSubscriptionResource resource) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long profileId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

    return new CreateSubscriptionCommand(
        resource.price(),
        resource.description(),
        profileId
    );
  }
}
