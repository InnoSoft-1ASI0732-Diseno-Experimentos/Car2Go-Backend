package com.pe.platform.payment.domain.services;

import com.pe.platform.payment.domain.model.aggregates.Subscription;
import com.pe.platform.payment.domain.model.queries.GetAllSubscriptionQuery;
import com.pe.platform.payment.domain.model.queries.GetSubscriptionByIdQuery;
import java.util.List;
import java.util.Optional;

/**
 * The interface Subscription query service.
 */
public interface SubscriptionQueryService {
  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<Subscription> handle(GetAllSubscriptionQuery query);

  /**
   * Handle optional.
   *
   * @param query the query
   * @return the optional
   */
  Optional<Subscription> handle(GetSubscriptionByIdQuery query);

  /**
   * Gets by profile id.
   *
   * @param profileId the profile id
   * @return the by profile id
   */
  Optional<Subscription> getByProfileId(Long profileId);

  /**
   * Gets all subscriptions.
   *
   * @return the all subscriptions
   */
  List<Subscription> getAllSubscriptions();

}
