package com.pe.platform.interaction.interfaces;

import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.interaction.domain.model.aggregates.Favorite;
import com.pe.platform.interaction.domain.services.FavoriteCommandService;
import com.pe.platform.interaction.infrastructure.persistence.jpa.FavoriteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Favorite controller.
 */
@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {

  private final FavoriteCommandService favoriteCommandService;
  private final FavoriteRepository favoriteRepository;

  /**
   * Instantiates a new Favorite controller.
   *
   * @param favoriteCommandService the favorite command service
   * @param favoriteRepository     the favorite repository
   */
  public FavoriteController(FavoriteCommandService favoriteCommandService,
                            FavoriteRepository favoriteRepository) {
    this.favoriteCommandService = favoriteCommandService;
    this.favoriteRepository = favoriteRepository;
  }


  /**
   * Add favorite response entity.
   *
   * @param vehicleId the vehicle id
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('ROLE_BUYER')")
  @PostMapping("/{vehicleId}")
  public ResponseEntity<Favorite> addFavorite(@PathVariable int vehicleId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    long userId = userDetails.getId();

    Optional<Favorite> favorite = favoriteCommandService.addFavorite(vehicleId, userId);

    return favorite.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }


  /**
   * Remove favorite response entity.
   *
   * @param vehicleId the vehicle id
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('ROLE_BUYER')")
  @DeleteMapping("/{vehicleId}")
  public ResponseEntity<Void> removeFavorite(@PathVariable int vehicleId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    long userId = userDetails.getId();

    favoriteCommandService.removeFavorite(vehicleId, userId);
    return ResponseEntity.noContent().build();
  }


  /**
   * Gets my favorites.
   *
   * @return the my favorites
   */
  @PreAuthorize("hasAuthority('ROLE_BUYER')")
  @GetMapping("/my-favorites")
  public ResponseEntity<List<Favorite>> getMyFavorites() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    long userId = userDetails.getId();


    List<Favorite> favorites = favoriteRepository.findByProfileId(userId);

    return ResponseEntity.ok(favorites);
  }
}
