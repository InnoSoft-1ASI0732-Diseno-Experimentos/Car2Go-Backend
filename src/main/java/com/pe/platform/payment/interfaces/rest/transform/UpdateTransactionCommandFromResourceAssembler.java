package com.pe.platform.payment.interfaces.rest.transform;

import com.pe.platform.payment.domain.model.commands.UpdateTransactionCommand;
import com.pe.platform.payment.interfaces.rest.resources.UpdateTransactionResource;

/**
 * The type Update transaction command from resource assembler.
 */
public class UpdateTransactionCommandFromResourceAssembler {
  /**
   * To command from resource update transaction command.
   *
   * @param resource the resource
   * @return the update transaction command
   */
  public static UpdateTransactionCommand toCommandFromResource(UpdateTransactionResource resource) {
    return new UpdateTransactionCommand(resource.transactionId(), resource.status());
  }
}
