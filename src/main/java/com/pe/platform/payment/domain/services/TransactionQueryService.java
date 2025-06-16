package com.pe.platform.payment.domain.services;

import com.pe.platform.payment.domain.model.aggregates.Transaction;
import com.pe.platform.payment.domain.model.queries.GetAllTransactionsQuery;
import com.pe.platform.payment.domain.model.queries.GetTransactionByIdQuery;
import java.util.List;
import java.util.Optional;

/**
 * The interface Transaction query service.
 */
public interface TransactionQueryService {
  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<Transaction> handle(GetAllTransactionsQuery query);

  /**
   * Handle optional.
   *
   * @param query the query
   * @return the optional
   */
  Optional<Transaction> handle(GetTransactionByIdQuery query);
}
