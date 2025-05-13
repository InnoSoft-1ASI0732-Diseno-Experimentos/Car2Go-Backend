package com.pe.platform.payment.domain.model.commands;

public record CreatePlanCommand(String name, Double price) {

    public CreatePlanCommand {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price cannot be null");
        }
    }
}
