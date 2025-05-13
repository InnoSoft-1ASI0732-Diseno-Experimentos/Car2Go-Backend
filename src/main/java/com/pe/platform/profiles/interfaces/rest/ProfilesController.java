package com.pe.platform.profiles.interfaces.rest;

import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.profiles.application.internal.commandservices.ProfileCommandServiceImpl;
import com.pe.platform.profiles.application.internal.queryservices.ProfileQueryServiceImpl;
import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.pe.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.pe.platform.profiles.domain.model.valueobjects.PaymentMethod;
import com.pe.platform.profiles.interfaces.rest.resources.CreateProfileResource;
import com.pe.platform.profiles.interfaces.rest.resources.ProfileResource;
import com.pe.platform.profiles.interfaces.rest.resources.UpdateProfileResource;
import com.pe.platform.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.pe.platform.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.pe.platform.profiles.interfaces.rest.transform.UpdateProfileCommandFromResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfilesController {
    private final ProfileCommandServiceImpl profileCommandService;
    private final ProfileQueryServiceImpl profileQueryService;

    public ProfilesController(ProfileCommandServiceImpl profileCommandService, ProfileQueryServiceImpl profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    @PostMapping
    public ResponseEntity<ProfileResource> createProfile(@RequestBody CreateProfileResource resource) {
        CreateProfileCommand createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<Profile> profile = profileCommandService.handle(createProfileCommand);
        if (profile.isEmpty()) return ResponseEntity.badRequest().build();
        ProfileResource profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResource> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var getProfileByIdQuery = new GetProfileByIdQuery(userDetails.getId());
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @PutMapping("/me/edit")
    public ResponseEntity<ProfileResource> updateProfile(@RequestBody UpdateProfileResource resource) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long userId = userDetails.getId();

        UpdateProfileCommand updateProfileCommand = UpdateProfileCommandFromResource.toCommandFromResource(resource);
        Optional<Profile> updatedProfileOptional = profileCommandService.handle(updateProfileCommand);

        return updatedProfileOptional
                .filter(updatedProfile -> updatedProfile.getProfileId() == userId)
                .map(updatedProfile -> ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(updatedProfile)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @PutMapping("/me/payment-methods/add")
    public ResponseEntity<?> addPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long userId = userDetails.getId();

        Optional<Profile> profileOptional = profileQueryService.handle(new GetProfileByIdQuery(userId));
        if (profileOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Profile not found for user with ID: " + userId);
        }

        Profile profile = profileOptional.get();

        if (profile.getPaymentMethods().size() >= 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot add more than 3 payment methods");
        }

        profile.addPaymentMethod(paymentMethod);
        profileCommandService.save(profile);

        ProfileResource profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile);
        return ResponseEntity.ok(profileResource);
    }


    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @PutMapping("/me/payment-methods/edit/{paymentMethodId}")
    public ResponseEntity<?> editPaymentMethod(@PathVariable Long paymentMethodId, @RequestBody PaymentMethod updatedPaymentMethod) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long userId = userDetails.getId();

        Optional<Profile> profileOptional = profileQueryService.handle(new GetProfileByIdQuery(userId));
        if (profileOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Profile profile = profileOptional.get();
        boolean updated = profile.updatePaymentMethod(paymentMethodId, updatedPaymentMethod);

        if (!updated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment method not found");
        }

        profileCommandService.save(profile);
        return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile));
    }

    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @DeleteMapping("/me/payment-methods/delete/{paymentMethodId}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable Long paymentMethodId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long userId = userDetails.getId();

        Optional<Profile> profileOptional = profileQueryService.handle(new GetProfileByIdQuery(userId));
        if (profileOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Profile profile = profileOptional.get();
        boolean removed = profile.removePaymentMethodById(paymentMethodId);

        if (!removed) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment method not found");
        }

        profileCommandService.save(profile);
        return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profileOptional = profileQueryService.handle(getProfileByIdQuery);

        if (profileOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profileOptional.get());
        return ResponseEntity.ok(profileResource);
    }
}
