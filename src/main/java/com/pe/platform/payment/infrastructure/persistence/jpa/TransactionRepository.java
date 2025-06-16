package com.pe.platform.payment.infrastructure.persistence.jpa;

import com.pe.platform.payment.domain.model.aggregates.Transaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Transaction repository.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  /**
   * Find all by buyer id list.
   *
   * @param buyerId the buyer id
   * @return the list
   */
  List<Transaction> findAllByBuyerId(Long buyerId);

  /**
   * Find all by seller id list.
   *
   * @param sellerId the seller id
   * @return the list
   */
  List<Transaction> findAllBySellerId(Long sellerId);

  /**
   * Find by id and buyer id optional.
   *
   * @param transactionId the transaction id
   * @param buyerId       the buyer id
   * @return the optional
   */
  Optional<Transaction> findByIdAndBuyerId(Long transactionId, Long buyerId);

  /**
   * Find by id and seller id optional.
   *
   * @param transactionId the transaction id
   * @param sellerId      the seller id
   * @return the optional
   */
  Optional<Transaction> findByIdAndSellerId(Long transactionId, Long sellerId);
}
