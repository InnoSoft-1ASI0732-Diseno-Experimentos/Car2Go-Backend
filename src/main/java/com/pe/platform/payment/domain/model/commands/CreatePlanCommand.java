package com.pe.platform.payment.domain.model.commands;

/**
 * The type Create plan command.
 */
public record CreatePlanCommand(String name, Double price) {

  /**
   * Instantiates a new Create plan command.
   *
   * @param name  the name
   * @param price the price
   */
  public CreatePlanCommand {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (price == null || price <= 0) {
      throw new IllegalArgumentException("Price cannot be null");
    }
  }
}
