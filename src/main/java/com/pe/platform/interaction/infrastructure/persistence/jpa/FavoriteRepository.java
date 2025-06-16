package com.pe.platform.interaction.infrastructure.persistence.jpa;

import com.pe.platform.interaction.domain.model.aggregates.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Favorite repository.
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
  /**
   * Find by profile id list.
   *
   * @param profileId the profile id
   * @return the list
   */
  List<Favorite> findByProfileId(long profileId);

  /**
   * Find by profile id and vehicle id optional.
   *
   * @param profileId the profile id
   * @param vehicleId the vehicle id
   * @return the optional
   */
  Optional<Favorite> findByProfileIdAndVehicleId(long profileId, int vehicleId);
}
