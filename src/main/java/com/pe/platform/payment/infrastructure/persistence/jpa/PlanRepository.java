package com.pe.platform.payment.infrastructure.persistence.jpa;

import com.pe.platform.payment.domain.model.entities.Plan;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Plan repository.
 */
public interface PlanRepository extends JpaRepository<Plan, Long> {
  /**
   * Find by name optional.
   *
   * @param name the name
   * @return the optional
   */
  Optional<Plan> findByName(String name);
}
