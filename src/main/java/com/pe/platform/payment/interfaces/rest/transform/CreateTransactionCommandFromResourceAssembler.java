package com.pe.platform.payment.interfaces.rest.transform;

import com.pe.platform.payment.domain.model.commands.CreateTransactionCommand;
import com.pe.platform.payment.interfaces.rest.resources.CreateTransactionResource;

/**
 * The type Create transaction command from resource assembler.
 */
public class CreateTransactionCommandFromResourceAssembler {
  /**
   * To command from resource create transaction command.
   *
   * @param resource the resource
   * @return the create transaction command
   */
  public static CreateTransactionCommand toCommandFromResource(CreateTransactionResource resource) {
    return new CreateTransactionCommand(
        resource.vehicleId()
    );
  }
}
