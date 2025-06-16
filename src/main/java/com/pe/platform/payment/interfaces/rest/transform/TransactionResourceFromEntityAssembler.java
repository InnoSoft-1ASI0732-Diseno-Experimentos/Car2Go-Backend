package com.pe.platform.payment.interfaces.rest.transform;

import com.pe.platform.payment.domain.model.aggregates.Transaction;
import com.pe.platform.payment.interfaces.rest.resources.TransactionResource;

/**
 * The type Transaction resource from entity assembler.
 */
public class TransactionResourceFromEntityAssembler {
  /**
   * To resource from entity transaction resource.
   *
   * @param entity the entity
   * @return the transaction resource
   */
  public static TransactionResource toResourceFromEntity(Transaction entity) {
    return new TransactionResource(
        entity.getId(),
        entity.getBuyer(),
        entity.getSeller(),
        entity.getVehicle(),
        entity.getAmount(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getPaymentStatus()
    );
  }
}
