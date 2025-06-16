package com.pe.platform.payment.interfaces.rest.resources;

/**
 * The type Update plan resource.
 */
public record UpdatePlanResource(Long planId, String name, Double price) {
}
