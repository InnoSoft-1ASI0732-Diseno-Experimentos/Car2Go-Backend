package com.pe.platform.payment.application.internal.services.queryservices;

import com.pe.platform.payment.domain.model.entities.Plan;
import com.pe.platform.payment.domain.model.queries.GetAllPlanQuery;
import com.pe.platform.payment.domain.model.queries.GetPlanByIdQuery;
import com.pe.platform.payment.domain.services.PlanQueryService;
import com.pe.platform.payment.infrastructure.persistence.jpa.PlanRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * The type Plan query service.
 */
@Service
public class PlanQueryServiceImpl implements PlanQueryService {
  private final PlanRepository planRepository;

  /**
   * Instantiates a new Plan query service.
   *
   * @param planRepository the plan repository
   */
  public PlanQueryServiceImpl(PlanRepository planRepository) {
    this.planRepository = planRepository;
  }


  @Override
  public Optional<Plan> handle(GetPlanByIdQuery query) {
    return planRepository.findById(query.id());
  }

  @Override
  public List<Plan> handle(GetAllPlanQuery query) {
    return planRepository.findAll();
  }

}
