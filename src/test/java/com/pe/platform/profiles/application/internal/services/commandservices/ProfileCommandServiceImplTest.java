package com.pe.platform.profiles.application.internal.services.commandservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.profiles.application.internal.commandservices.ProfileCommandServiceImpl;
import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.pe.platform.profiles.domain.model.valueobjects.PaymentMethod;
import com.pe.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The type Profile command service impl test.
 */
@ExtendWith(MockitoExtension.class)
class ProfileCommandServiceImplTest {

  private final Long USER_ID = 1L;
  @Mock
  private ProfileRepository profileRepository;
  @Mock
  private Authentication authentication;
  @Mock
  private SecurityContext securityContext;
  @InjectMocks
  private ProfileCommandServiceImpl profileCommandService;
  private UserDetailsImpl userDetails;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    // Configura el contexto de seguridad simulado
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    userDetails = new UserDetailsImpl(USER_ID, "testuser", "test@example.com", authorities);
  }

  /**
   * Test handle create profile command.
   */
  @Test
  @DisplayName("Debería crear un perfil correctamente")
  void testHandleCreateProfileCommand() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetails);

      CreateProfileCommand command = createSampleCommand();
      Profile profile = new Profile(command, USER_ID);

      when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.empty());
      when(profileRepository.findByEmail(command.email())).thenReturn(Optional.empty());
      when(profileRepository.save(any(Profile.class))).thenReturn(profile);

      // Act
      Optional<Profile> result = profileCommandService.handle(command);

      // Assert
      assertTrue(result.isPresent());
      assertEquals(USER_ID, result.get().getProfileId());
      assertEquals(command.firstName(), result.get().getFirstName());
      assertEquals(command.lastName(), result.get().getLastName());

      verify(profileRepository, times(1)).findByProfileId(USER_ID);
      verify(profileRepository, times(1)).findByEmail(command.email());
      verify(profileRepository, times(1)).save(any(Profile.class));
    }
  }

  /**
   * Test handle create profile command when profile exists.
   */
  @Test
  @DisplayName("Debería lanzar excepción al crear un perfil cuando ya existe uno para el usuario")
  void testHandleCreateProfileCommandWhenProfileExists() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetails);

      CreateProfileCommand command = createSampleCommand();
      Profile existingProfile = new Profile(command, USER_ID);

      when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.of(existingProfile));

      // Act & Assert
      assertThrows(IllegalArgumentException.class, () -> profileCommandService.handle(command));

      verify(profileRepository, times(1)).findByProfileId(USER_ID);
      verify(profileRepository, never()).save(any(Profile.class));
    }
  }

  /**
   * Test handle create profile command with existing email.
   */
  @Test
  @DisplayName("Debería lanzar excepción al crear un perfil cuando el email ya existe")
  void testHandleCreateProfileCommandWithExistingEmail() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetails);

      CreateProfileCommand command = createSampleCommand();
      Profile existingProfile = new Profile(command, 2L); // Otro usuario con el mismo email

      when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.empty());
      when(profileRepository.findByEmail(command.email())).thenReturn(Optional.of(existingProfile));

      // Act & Assert
      assertThrows(IllegalArgumentException.class, () -> profileCommandService.handle(command));

      verify(profileRepository, times(1)).findByProfileId(USER_ID);
      verify(profileRepository, times(1)).findByEmail(command.email());
      verify(profileRepository, never()).save(any(Profile.class));
    }
  }

  /**
   * Test handle update profile command.
   */
  @Test
  @DisplayName("Debería actualizar un perfil correctamente")
  void testHandleUpdateProfileCommand() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetails);

      UpdateProfileCommand command = createSampleUpdateCommand();
      Profile existingProfile = new Profile(createSampleCommand(), USER_ID);
      Profile updatedProfile = new Profile(command);
      updatedProfile.setProfileId(USER_ID);

      when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.of(existingProfile));
      when(profileRepository.save(any(Profile.class))).thenReturn(updatedProfile);

      // Act
      Optional<Profile> result = profileCommandService.handle(command);

      // Assert
      assertTrue(result.isPresent());
      assertEquals(USER_ID, result.get().getProfileId());
      assertEquals(command.firstName(), result.get().getFirstName());
      assertEquals(command.lastName(), result.get().getLastName());

      verify(profileRepository, times(1)).findByProfileId(USER_ID);
      verify(profileRepository, times(1)).save(any(Profile.class));
    }
  }

  /**
   * Test handle update profile command non existent profile.
   */
  @Test
  @DisplayName("Debería lanzar excepción al actualizar un perfil que no existe")
  void testHandleUpdateProfileCommandNonExistentProfile() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetails);

      UpdateProfileCommand command = createSampleUpdateCommand();

      when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.empty());

      // Act & Assert
      assertThrows(IllegalArgumentException.class, () -> profileCommandService.handle(command));

      verify(profileRepository, times(1)).findByProfileId(USER_ID);
      verify(profileRepository, never()).save(any(Profile.class));
    }
  }

  /**
   * Test add payment method.
   */
  @Test
  @DisplayName("Debería agregar un método de pago correctamente")
  void testAddPaymentMethod() {
    // Arrange
    PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");
    Profile profile = new Profile(createSampleCommand(), USER_ID);

    when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.of(profile));
    when(profileRepository.save(any(Profile.class))).thenReturn(profile);

    // Act
    Optional<Profile> result = profileCommandService.addPaymentMethod(USER_ID, paymentMethod);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(1, result.get().getPaymentMethods().size());
    assertEquals(paymentMethod.getType(), result.get().getPaymentMethods().get(0).getType());
    assertEquals(paymentMethod.getDetails(), result.get().getPaymentMethods().get(0).getDetails());

    verify(profileRepository, times(1)).findByProfileId(USER_ID);
    verify(profileRepository, times(1)).save(profile);
  }

  /**
   * Test add payment method to non existent profile.
   */
  @Test
  @DisplayName("Debería lanzar excepción al agregar un método de pago a un perfil que no existe")
  void testAddPaymentMethodToNonExistentProfile() {
    // Arrange
    PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");

    when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(IllegalArgumentException.class,
        () -> profileCommandService.addPaymentMethod(USER_ID, paymentMethod));

    verify(profileRepository, times(1)).findByProfileId(USER_ID);
    verify(profileRepository, never()).save(any(Profile.class));
  }

  /**
   * Test add payment method exceeding limit.
   */
  @Test
  @DisplayName("Debería lanzar excepción al agregar más de 3 métodos de pago")
  void testAddPaymentMethodExceedingLimit() {
    // Arrange
    Profile profile = new Profile(createSampleCommand(), USER_ID);
    profile.addPaymentMethod(new PaymentMethod("VISA", "1111-2222-3333-4444"));
    profile.addPaymentMethod(new PaymentMethod("MASTERCARD", "5555-6666-7777-8888"));
    profile.addPaymentMethod(new PaymentMethod("AMEX", "9999-0000-1111-2222"));

    PaymentMethod extraPaymentMethod = new PaymentMethod("PAYPAL", "user@example.com");

    when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.of(profile));

    // Act & Assert
    assertThrows(IllegalArgumentException.class,
        () -> profileCommandService.addPaymentMethod(USER_ID, extraPaymentMethod));

    verify(profileRepository, times(1)).findByProfileId(USER_ID);
    verify(profileRepository, never()).save(any(Profile.class));
  }

  /**
   * Test remove payment method.
   */
  @Test
  @DisplayName("Debería eliminar un método de pago correctamente")
  void testRemovePaymentMethod() {
    // Arrange
    PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");
    Profile profile = new Profile(createSampleCommand(), USER_ID);
    profile.addPaymentMethod(paymentMethod);

    when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.of(profile));
    when(profileRepository.save(any(Profile.class))).thenReturn(profile);

    // Act
    Optional<Profile> result = profileCommandService.removePaymentMethod(USER_ID, paymentMethod);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(0, result.get().getPaymentMethods().size());

    verify(profileRepository, times(1)).findByProfileId(USER_ID);
    verify(profileRepository, times(1)).save(profile);
  }

  /**
   * Test get profile by id.
   */
  @Test
  @DisplayName("Debería obtener un perfil por ID")
  void testGetProfileById() {
    // Arrange
    Profile profile = new Profile(createSampleCommand(), USER_ID);

    when(profileRepository.findByProfileId(USER_ID)).thenReturn(Optional.of(profile));

    // Act
    Optional<Profile> result = profileCommandService.getProfileById(USER_ID);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(profile, result.get());

    verify(profileRepository, times(1)).findByProfileId(USER_ID);
  }

  /**
   * Test save.
   */
  @Test
  @DisplayName("Debería guardar un perfil")
  void testSave() {
    // Arrange
    Profile profile = new Profile(createSampleCommand(), USER_ID);

    when(profileRepository.save(profile)).thenReturn(profile);

    // Act
    Optional<Profile> result = profileCommandService.save(profile);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(profile, result.get());

    verify(profileRepository, times(1)).save(profile);
  }

  /**
   * Crea un comando de muestra para las pruebas
   */
  private CreateProfileCommand createSampleCommand() {
    return new CreateProfileCommand(
        "John",
        "Doe",
        "john.doe@example.com",
        "profile-image.jpg",
        "12345678",
        "Calle Principal 123",
        "+51987654321"
    );
  }

  /**
   * Crea un comando de actualización de muestra para las pruebas
   */
  private UpdateProfileCommand createSampleUpdateCommand() {
    return new UpdateProfileCommand(
        "Jane",
        "Smith",
        "jane.smith@example.com",
        "new-profile-image.jpg",
        "87654321",
        "Avenida Secundaria 456",
        "+51123456789"
    );
  }
} 