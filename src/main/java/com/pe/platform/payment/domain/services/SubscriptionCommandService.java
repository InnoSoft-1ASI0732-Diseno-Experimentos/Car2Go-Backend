package com.pe.platform.payment.domain.services;

import com.pe.platform.payment.domain.model.aggregates.Subscription;
import com.pe.platform.payment.domain.model.commands.CreateSubscriptionCommand;
import com.pe.platform.payment.domain.model.commands.UpdateSubscriptionCommand;
import java.util.Optional;

/**
 * The interface Subscription command service.
 */
public interface SubscriptionCommandService {
  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<Subscription> handle(CreateSubscriptionCommand command);

  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<Subscription> handle(UpdateSubscriptionCommand command);
}