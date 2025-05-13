package com.pe.platform.profiles.application.internal.commandservices;

import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.pe.platform.profiles.domain.model.valueobjects.PaymentMethod;
import com.pe.platform.profiles.domain.services.ProfileCommandService;
import com.pe.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(CreateProfileCommand command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<Profile> existingProfile = profileRepository.findByProfileId(userDetails.getId());
        if (existingProfile.isPresent()) {
            throw new IllegalArgumentException("A profile already exists for this user");
        }

        Optional<Profile> profilesByEmail = profileRepository.findByEmail(command.email());
        if (profilesByEmail.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Profile profile = new Profile(command, userDetails.getId());
        profileRepository.save(profile);

        return Optional.of(profile);
    }

    @Override
    public Optional<Profile> handle(UpdateProfileCommand command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<Profile> existingProfile = profileRepository.findByProfileId(userDetails.getId());
        if (existingProfile.isEmpty()) {
            throw new IllegalArgumentException("No profile found for this user");
        }

        Profile profile = existingProfile.get();
        profile.updateName(command.firstName(), command.lastName());

        profileRepository.save(profile);
        return Optional.of(profile);
    }

    @Override
    public Optional<Profile> addPaymentMethod(long profileId, PaymentMethod paymentMethod) {
        Optional<Profile> profileOptional = profileRepository.findByProfileId(profileId);
        if (profileOptional.isEmpty()) {
            throw new IllegalArgumentException("Profile not found");
        }

        Profile profile = profileOptional.get();
        if (profile.getPaymentMethods().size() >= 3) {
            throw new IllegalArgumentException("Cannot add more than 3 payment methods");
        }

        profile.addPaymentMethod(paymentMethod);
        profileRepository.save(profile);

        return Optional.of(profile);
    }

    @Override
    public Optional<Profile> removePaymentMethod(long profileId, PaymentMethod paymentMethod) {
        Optional<Profile> profileOptional = profileRepository.findByProfileId(profileId);
        if (profileOptional.isEmpty()) {
            throw new IllegalArgumentException("Profile not found");
        }

        Profile profile = profileOptional.get();
        profile.getPaymentMethods().removeIf(existingMethod ->
                existingMethod.getType().equals(paymentMethod.getType()) &&
                        existingMethod.getDetails().equals(paymentMethod.getDetails())
        );

        profileRepository.save(profile);
        return Optional.of(profile);
    }

    @Override
    public Optional<Profile> save(Profile profile) {
        return Optional.of(profileRepository.save(profile));
    }


    @Override
    public Optional<Profile> getProfileById(long profileId) {
        return profileRepository.findByProfileId(profileId);
    }


}
