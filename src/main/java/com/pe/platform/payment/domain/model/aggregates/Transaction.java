package com.pe.platform.payment.domain.model.aggregates;

import com.pe.platform.payment.domain.model.valueobjects.PaymentStatus;
import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Transaction.
 */
@Getter
@Setter
@Entity
public class Transaction extends AuditableAbstractAggregateRoot {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus paymentStatus;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "buyer_id", nullable = false)
  private Profile buyer;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "seller_id", nullable = false)
  private Profile seller;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "vehicle_id", nullable = false)
  private Vehicle vehicle;

  @Column(nullable = false)
  private Double amount;

  /**
   * Instantiates a new Transaction.
   */
  public Transaction() {
  }

  /**
   * Instantiates a new Transaction.
   *
   * @param buyer   the buyer
   * @param seller  the seller
   * @param vehicle the vehicle
   * @param amount  the amount
   */
  public Transaction(Profile buyer, Profile seller, Vehicle vehicle, Double amount) {
    this.buyer = buyer;
    this.seller = seller;
    this.vehicle = vehicle;
    this.amount = amount;
    this.paymentStatus = PaymentStatus.PENDING;
  }


  /**
   * Sets status.
   *
   * @param status the status
   */
  public void setStatus(PaymentStatus status) {
    this.paymentStatus = status;
  }
}
