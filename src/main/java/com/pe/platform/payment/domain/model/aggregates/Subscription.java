package com.pe.platform.payment.domain.model.aggregates;

import com.pe.platform.payment.domain.model.valueobjects.SubscriptionStatus;
import com.pe.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * The type Subscription.
 */
@Entity
public class Subscription extends AuditableAbstractAggregateRoot<Subscription> {

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private SubscriptionStatus status;

  @Column(nullable = false)
  private Long profileId;

  /**
   * Instantiates a new Subscription.
   */
  public Subscription() {
  }

  /**
   * Instantiates a new Subscription.
   *
   * @param price       the price
   * @param description the description
   * @param status      the status
   * @param profileId   the profile id
   */
  public Subscription(Integer price, String description, SubscriptionStatus status,
                      Long profileId) {
    this.price = price;
    this.description = description;
    this.status = status;
    this.profileId = profileId;
  }

  /**
   * Gets price.
   *
   * @return the price
   */
  public Integer getPrice() {
    return price;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets status.
   *
   * @return the status
   */
  public SubscriptionStatus getStatus() {
    return status;
  }

  /**
   * Sets status.
   *
   * @param status the status
   */
  public void setStatus(SubscriptionStatus status) {
    this.status = status;
  }

  /**
   * Gets profile id.
   *
   * @return the profile id
   */
  public Long getProfileId() {
    return profileId;
  }
}
