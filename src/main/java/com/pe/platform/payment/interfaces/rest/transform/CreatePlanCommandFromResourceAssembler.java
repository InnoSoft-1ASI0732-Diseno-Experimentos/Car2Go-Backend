package com.pe.platform.payment.interfaces.rest.transform;

import com.pe.platform.payment.domain.model.commands.CreatePlanCommand;
import com.pe.platform.payment.interfaces.rest.resources.CreatePlanResource;

/**
 * The type Create plan command from resource assembler.
 */
public class CreatePlanCommandFromResourceAssembler {
  /**
   * To command from resource create plan command.
   *
   * @param resource the resource
   * @return the create plan command
   */
  public static CreatePlanCommand toCommandFromResource(CreatePlanResource resource) {

    return new CreatePlanCommand(
        resource.name(),
        resource.price());
  }
}
