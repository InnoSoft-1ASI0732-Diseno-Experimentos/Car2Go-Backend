package com.pe.platform.interaction.domain.services;

import com.pe.platform.interaction.domain.model.aggregates.Favorite;
import java.util.Optional;

/**
 * The interface Favorite command service.
 */
public interface FavoriteCommandService {
  /**
   * Add favorite optional.
   *
   * @param vehicleId the vehicle id
   * @param profileId the profile id
   * @return the optional
   */
  Optional<Favorite> addFavorite(int vehicleId, long profileId);

  /**
   * Remove favorite.
   *
   * @param vehicleId the vehicle id
   * @param profileId the profile id
   */
  void removeFavorite(int vehicleId, long profileId);
}
