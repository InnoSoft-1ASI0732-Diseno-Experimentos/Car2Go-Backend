package com.pe.platform.payment.domain.services;

import com.pe.platform.payment.domain.model.aggregates.Transaction;
import com.pe.platform.payment.domain.model.commands.CreateTransactionCommand;
import com.pe.platform.payment.domain.model.commands.UpdateTransactionCommand;
import java.util.Optional;

/**
 * The interface Transaction command service.
 */
public interface TransactionCommandService {
  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<Transaction> handle(CreateTransactionCommand command);

  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<Transaction> handle(UpdateTransactionCommand command);
}
