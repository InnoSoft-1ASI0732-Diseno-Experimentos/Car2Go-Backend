package com.pe.platform.payment.interfaces.acl;

import com.pe.platform.payment.domain.services.SubscriptionCommandService;
import org.springframework.stereotype.Service;

/**
 * The type Subscription context facade.
 */
@Service

public class SubscriptionContextFacade {
  private final SubscriptionCommandService subscriptionCommandService;


  /**
   * Instantiates a new Subscription context facade.
   *
   * @param subscriptionCommandService the subscription command service
   */
  public SubscriptionContextFacade(SubscriptionCommandService subscriptionCommandService) {
    this.subscriptionCommandService = subscriptionCommandService;
  }

    /*
    *
    *   public Long createProfile(String firstName, String lastName, String direction, String phone, String gender, String birthDate,
    * String documentNumber, String documentType, String role) {
       var createProfileCommand = new CreateProfileCommnad( firstName,  lastName,  direction,  phone,  gender,  birthDate,  documentNumber,
       *   documentType, role);
       var profile= profileCommandService.handle(createProfileCommand);
        if (profile.isEmpty()) return 0L;
        return profile.get().getId();
    }

    *

    public Long createSubscription(Integer price, String description, Boolean paid) {

        var createSubscriptionCommand = new CreateSubscriptionCommand(price, description, paid);
        var subscription = subscriptionCommandService.handle(createSubscriptionCommand);
        if (subscription.isEmpty()) return 0L;
        return subscription.get().getId();
    }
*/
}
