package com.pe.platform.payment.domain.model.commands;

/**
 * The type Update plan command.
 */
public record UpdatePlanCommand(Long planId, String name, Double price) {

  /**
   * Instantiates a new Update plan command.
   *
   * @param planId the plan id
   * @param name   the name
   * @param price  the price
   */
  public UpdatePlanCommand {
    if (planId == null) {
      throw new IllegalArgumentException("PlanId cannot be null");
    }
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (price == null || price <= 0) {
      throw new IllegalArgumentException("Price cannot be null");
    }
  }
}
