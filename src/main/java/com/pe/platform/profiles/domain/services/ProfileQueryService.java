package com.pe.platform.profiles.domain.services;

import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.pe.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import java.util.List;
import java.util.Optional;

/**
 * The interface Profile query service.
 */
public interface ProfileQueryService {
  /**
   * Handle optional.
   *
   * @param query the query
   * @return the optional
   */
  Optional<Profile> handle(GetProfileByIdQuery query);

  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<Profile> handle(GetAllProfilesQuery query);

}
