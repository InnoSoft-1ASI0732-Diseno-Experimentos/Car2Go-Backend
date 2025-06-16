package com.pe.platform.payment.domain.model.commands;

import com.pe.platform.payment.domain.model.valueobjects.SubscriptionStatus;

/**
 * The type Update subscription command.
 */
public record UpdateSubscriptionCommand(Long profileId, SubscriptionStatus status) {
}
