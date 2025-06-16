package com.pe.platform.payment.domain.model.entities;

import com.pe.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Plan.
 */
@Entity
@Getter
@Setter
public class Plan extends AuditableAbstractAggregateRoot<Plan> {


  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private Double price;

  /**
   * Instantiates a new Plan.
   */
  public Plan() {
  }

  /**
   * Instantiates a new Plan.
   *
   * @param name  the name
   * @param price the price
   */
  public Plan(String name, Double price) {
    this.name = name;
    this.price = price;
  }

}
