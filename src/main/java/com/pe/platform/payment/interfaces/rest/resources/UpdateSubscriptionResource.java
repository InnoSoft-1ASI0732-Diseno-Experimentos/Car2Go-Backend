package com.pe.platform.payment.interfaces.rest.resources;

/**
 * The type Update subscription resource.
 */
public record UpdateSubscriptionResource(
    String firstName, String lastName, String direction, String phone, String gender,
    String birthDate,
    String documentNumber, String documentType, String role, Integer price, String description,
    Boolean paid
) {
}

