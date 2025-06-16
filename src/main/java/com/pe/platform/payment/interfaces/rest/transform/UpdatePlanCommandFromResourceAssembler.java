package com.pe.platform.payment.interfaces.rest.transform;

import com.pe.platform.payment.domain.model.commands.UpdatePlanCommand;
import com.pe.platform.payment.interfaces.rest.resources.UpdatePlanResource;

/**
 * The type Update plan command from resource assembler.
 */
public class UpdatePlanCommandFromResourceAssembler {
  /**
   * To command from resource update plan command.
   *
   * @param resource the resource
   * @return the update plan command
   */
  public static UpdatePlanCommand toCommandFromResource(UpdatePlanResource resource) {

    return new UpdatePlanCommand(
        resource.planId(),
        resource.name(),
        resource.price());
  }
}
