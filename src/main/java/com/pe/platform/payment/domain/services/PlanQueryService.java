package com.pe.platform.payment.domain.services;

import com.pe.platform.payment.domain.model.entities.Plan;
import com.pe.platform.payment.domain.model.queries.GetAllPlanQuery;
import com.pe.platform.payment.domain.model.queries.GetPlanByIdQuery;
import java.util.List;
import java.util.Optional;

/**
 * The interface Plan query service.
 */
public interface PlanQueryService {
  /**
   * Handle optional.
   *
   * @param query the query
   * @return the optional
   */
  Optional<Plan> handle(GetPlanByIdQuery query);

  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<Plan> handle(GetAllPlanQuery query);
}
