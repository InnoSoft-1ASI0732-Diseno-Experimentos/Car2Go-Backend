package com.pe.platform.profiles.application.internal.services.queryservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pe.platform.profiles.application.internal.queryservices.ProfileQueryServiceImpl;
import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.pe.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.pe.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Profile query service impl test.
 */
@ExtendWith(MockitoExtension.class)
class ProfileQueryServiceImplTest {

  private final Long PROFILE_ID = 1L;
  @Mock
  private ProfileRepository profileRepository;
  @InjectMocks
  private ProfileQueryServiceImpl profileQueryService;
  private Profile sampleProfile1;
  private Profile sampleProfile2;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    // Crear perfiles de muestra para pruebas
    sampleProfile1 = createSampleProfile(1L, PROFILE_ID);
    sampleProfile2 = createSampleProfile(2L, 2L);
  }

  /**
   * Test handle get profile by id query.
   */
  @Test
  @DisplayName("Debería obtener un perfil por ID")
  void testHandleGetProfileByIdQuery() {
    // Arrange
    GetProfileByIdQuery query = new GetProfileByIdQuery(PROFILE_ID);
    when(profileRepository.findById(PROFILE_ID)).thenReturn(Optional.of(sampleProfile1));

    // Act
    Optional<Profile> result = profileQueryService.handle(query);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(sampleProfile1, result.get());
    assertEquals(PROFILE_ID, result.get().getProfileId());

    verify(profileRepository, times(1)).findById(PROFILE_ID);
  }

  /**
   * Test handle get profile by id query not found.
   */
  @Test
  @DisplayName("Debería retornar Optional vacío cuando no existe perfil con el ID")
  void testHandleGetProfileByIdQueryNotFound() {
    // Arrange
    Long nonExistentId = 999L;
    GetProfileByIdQuery query = new GetProfileByIdQuery(nonExistentId);
    when(profileRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    // Act
    Optional<Profile> result = profileQueryService.handle(query);

    // Assert
    assertFalse(result.isPresent());

    verify(profileRepository, times(1)).findById(nonExistentId);
  }

  /**
   * Test handle get all profiles query.
   */
  @Test
  @DisplayName("Debería obtener todos los perfiles")
  void testHandleGetAllProfilesQuery() {
    // Arrange
    List<Profile> expectedProfiles = Arrays.asList(sampleProfile1, sampleProfile2);
    GetAllProfilesQuery query = new GetAllProfilesQuery();
    when(profileRepository.findAll()).thenReturn(expectedProfiles);

    // Act
    List<Profile> result = profileQueryService.handle(query);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(expectedProfiles, result);

    verify(profileRepository, times(1)).findAll();
  }

  /**
   * Test handle get all profiles query empty.
   */
  @Test
  @DisplayName("Debería retornar lista vacía cuando no hay perfiles")
  void testHandleGetAllProfilesQueryEmpty() {
    // Arrange
    GetAllProfilesQuery query = new GetAllProfilesQuery();
    when(profileRepository.findAll()).thenReturn(Collections.emptyList());

    // Act
    List<Profile> result = profileQueryService.handle(query);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(profileRepository, times(1)).findAll();
  }

  /**
   * Crea un perfil de muestra para pruebas
   */
  private Profile createSampleProfile(Long id, Long profileId) {
    CreateProfileCommand command = new CreateProfileCommand(
        "Test User " + id,
        "Apellido " + id,
        "test" + id + "@example.com",
        "profile-image-" + id + ".jpg",
        "1234567" + id,
        "Calle " + id,
        "+51987654" + id
    );

    Profile profile = new Profile(command, profileId);

    // Simular ID que normalmente sería establecido por JPA
    try {
      java.lang.reflect.Field idField = Profile.class.getDeclaredField("id");
      idField.setAccessible(true);
      idField.set(profile, id.intValue());
    } catch (Exception e) {
      throw new RuntimeException("Error setting profile ID: " + e.getMessage());
    }

    return profile;
  }
} 