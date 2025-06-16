package com.pe.platform.profiles.application.internal.queryservices;

import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.pe.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.pe.platform.profiles.domain.services.ProfileQueryService;
import com.pe.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * The type Profile query service.
 */
@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
  private final ProfileRepository profileRepository;

  /**
   * Instantiates a new Profile query service.
   *
   * @param profileRepository the profile repository
   */
  public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  @Override
  public Optional<Profile> handle(GetProfileByIdQuery query) {
    return profileRepository.findById(query.profileId());
  }

  @Override
  public List<Profile> handle(GetAllProfilesQuery query) {
    return profileRepository.findAll();
  }

}
