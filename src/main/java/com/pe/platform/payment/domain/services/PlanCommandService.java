package com.pe.platform.payment.domain.services;

import com.pe.platform.payment.domain.model.commands.CreatePlanCommand;
import com.pe.platform.payment.domain.model.commands.UpdatePlanCommand;
import com.pe.platform.payment.domain.model.entities.Plan;
import java.util.Optional;

/**
 * The interface Plan command service.
 */
public interface PlanCommandService {
  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<Plan> handle(CreatePlanCommand command);

  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<Plan> handle(UpdatePlanCommand command);
}
