package com.pe.platform.interaction.infrastructure.persistence.jpa;

import com.pe.platform.interaction.domain.model.aggregates.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Review repository.
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
  /**
   * Find by vehicle id list.
   *
   * @param vehicleId the vehicle id
   * @return the list
   */
  List<Review> findByVehicleId(int vehicleId);


}
