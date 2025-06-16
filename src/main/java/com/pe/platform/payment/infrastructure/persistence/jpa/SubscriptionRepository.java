package com.pe.platform.payment.infrastructure.persistence.jpa;

import com.pe.platform.payment.domain.model.aggregates.Subscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Subscription repository.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  /**
   * Find by profile id optional.
   *
   * @param profileId the profile id
   * @return the optional
   */
  Optional<Subscription> findByProfileId(Long profileId);
}
