package com.pe.platform.payment.interfaces.rest.resources;

import com.pe.platform.payment.domain.model.valueobjects.PaymentStatus;

/**
 * The type Update transaction resource.
 */
public record UpdateTransactionResource(Long transactionId, PaymentStatus status) {
}
