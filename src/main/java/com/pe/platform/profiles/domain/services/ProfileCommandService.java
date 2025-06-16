package com.pe.platform.profiles.domain.services;

import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.pe.platform.profiles.domain.model.valueobjects.PaymentMethod;
import java.util.Optional;

/**
 * The interface Profile command service.
 */
public interface ProfileCommandService {
  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<Profile> handle(CreateProfileCommand command);

  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<Profile> handle(UpdateProfileCommand command);

  /**
   * Add payment method optional.
   *
   * @param profileId     the profile id
   * @param paymentMethod the payment method
   * @return the optional
   */
  Optional<Profile> addPaymentMethod(long profileId, PaymentMethod paymentMethod);

  /**
   * Remove payment method optional.
   *
   * @param profileId     the profile id
   * @param paymentMethod the payment method
   * @return the optional
   */
  Optional<Profile> removePaymentMethod(long profileId, PaymentMethod paymentMethod);

  /**
   * Save optional.
   *
   * @param profile the profile
   * @return the optional
   */
  Optional<Profile> save(Profile profile);

  /**
   * Gets profile by id.
   *
   * @param profileId the profile id
   * @return the profile by id
   */
  Optional<Profile> getProfileById(long profileId); // Nuevo método
}
