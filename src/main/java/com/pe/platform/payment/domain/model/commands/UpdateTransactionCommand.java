package com.pe.platform.payment.domain.model.commands;

import com.pe.platform.payment.domain.model.valueobjects.PaymentStatus;

/**
 * The type Update transaction command.
 */
public record UpdateTransactionCommand(Long transactionId, PaymentStatus status) {
  /**
   * Instantiates a new Update transaction command.
   *
   * @param transactionId the transaction id
   * @param status        the status
   */
  public UpdateTransactionCommand {
    if (transactionId == null) {
      throw new IllegalArgumentException("TransactionId cannot be null");
    }
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null");
    }
  }
}
