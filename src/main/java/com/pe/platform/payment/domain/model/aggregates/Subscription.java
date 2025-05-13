package com.pe.platform.payment.domain.model.aggregates;

import com.pe.platform.payment.domain.model.valueobjects.SubscriptionStatus;
import com.pe.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

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

    public Subscription() {}

    public Subscription(Integer price, String description, SubscriptionStatus status, Long profileId) {
        this.price = price;
        this.description = description;
        this.status = status;
        this.profileId = profileId;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
}
