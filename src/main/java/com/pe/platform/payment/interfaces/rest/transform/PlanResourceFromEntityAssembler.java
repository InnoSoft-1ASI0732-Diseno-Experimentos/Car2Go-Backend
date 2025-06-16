package com.pe.platform.payment.interfaces.rest.transform;

import com.pe.platform.payment.domain.model.entities.Plan;
import com.pe.platform.payment.interfaces.rest.resources.PlanResource;

/**
 * The type Plan resource from entity assembler.
 */
public class PlanResourceFromEntityAssembler {
  /**
   * To resource from entity plan resource.
   *
   * @param entity the entity
   * @return the plan resource
   */
  public static PlanResource toResourceFromEntity(Plan entity) {
    return new PlanResource(entity.getName(), entity.getPrice());
  }
}
