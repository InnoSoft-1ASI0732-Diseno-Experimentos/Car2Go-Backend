package com.pe.platform.profiles.infrastructure.persistence.jpa.repositories;

import com.pe.platform.profiles.domain.model.aggregates.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Profile repository.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  /**
   * Find by email optional.
   *
   * @param email the email
   * @return the optional
   */
  Optional<Profile> findByEmail(String email);

  Optional<Profile> findById(Long id);

  /**
   * Find by profile id optional.
   *
   * @param profileId the profile id
   * @return the optional
   */
  Optional<Profile> findByProfileId(long profileId);


  /**
   * Can add payment method boolean.
   *
   * @param profile the profile
   * @return the boolean
   */
  default boolean canAddPaymentMethod(Profile profile) {
    return profile.getPaymentMethods().size() < 3;
  }
}
