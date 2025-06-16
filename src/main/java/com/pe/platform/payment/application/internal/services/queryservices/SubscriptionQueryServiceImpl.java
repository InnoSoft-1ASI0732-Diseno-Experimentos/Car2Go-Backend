package com.pe.platform.payment.application.internal.services.queryservices;

import com.pe.platform.payment.domain.model.aggregates.Subscription;
import com.pe.platform.payment.domain.model.queries.GetAllSubscriptionQuery;
import com.pe.platform.payment.domain.model.queries.GetSubscriptionByIdQuery;
import com.pe.platform.payment.domain.services.SubscriptionQueryService;
import com.pe.platform.payment.infrastructure.persistence.jpa.SubscriptionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * The type Subscription query service.
 */
@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {
  private final SubscriptionRepository subscriptionRepository;

  /**
   * Instantiates a new Subscription query service.
   *
   * @param subscriptionRepository the subscription repository
   */
  public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  @Override
  public List<Subscription> handle(GetAllSubscriptionQuery query) {
    return subscriptionRepository.findAll();
  }

  @Override
  public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
    return subscriptionRepository.findById(query.id());
  }

  @Override
  public List<Subscription> getAllSubscriptions() {
    return subscriptionRepository.findAll();
  }

  @Override
  public Optional<Subscription> getByProfileId(Long profileId) {
    return subscriptionRepository.findByProfileId(profileId);
  }
}
