package com.pe.platform.payment.domain.model.commands;

/**
 * The type Create subscription command.
 */
public record CreateSubscriptionCommand(Integer price, String description, Long profileId) {
  /**
   * Instantiates a new Create subscription command.
   *
   * @param price       the price
   * @param description the description
   * @param profileId   the profile id
   */
  public CreateSubscriptionCommand {
    if (price == null || price <= 0) {
      throw new IllegalArgumentException("Price cannot be null or zero");
    }
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null");
    }
    if (profileId == null) {
      throw new IllegalArgumentException("ProfileId cannot be null");
    }
  }
}
