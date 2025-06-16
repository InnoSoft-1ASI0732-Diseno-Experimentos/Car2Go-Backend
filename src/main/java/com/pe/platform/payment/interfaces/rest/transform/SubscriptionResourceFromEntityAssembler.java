package com.pe.platform.payment.interfaces.rest.transform;

import com.pe.platform.payment.domain.model.aggregates.Subscription;
import com.pe.platform.payment.interfaces.rest.resources.SubscriptionResource;

/**
 * The type Subscription resource from entity assembler.
 */
public class SubscriptionResourceFromEntityAssembler {
  /**
   * To resource from entity subscription resource.
   *
   * @param entity the entity
   * @return the subscription resource
   */
  public static SubscriptionResource toResourceFromEntity(Subscription entity) {
    return new SubscriptionResource(entity.getPrice(), entity.getDescription(), entity.getStatus(),
        entity.getProfileId());
  }
}
