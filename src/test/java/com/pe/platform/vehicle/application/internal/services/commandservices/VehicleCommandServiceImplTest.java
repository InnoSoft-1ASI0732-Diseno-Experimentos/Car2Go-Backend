package com.pe.platform.vehicle.application.internal.services.commandservices;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;
import com.pe.platform.vehicle.domain.model.valueobjects.vehicleStatus;
import com.pe.platform.vehicle.infrastructure.persistence.jpa.VehicleRepository;
import java.util.ArrayList;
import java.util.Arrays;
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
 * The type Vehicle command service impl test.
 */
@ExtendWith(MockitoExtension.class)
class VehicleCommandServiceImplTest {

  private final Long USER_ID = 1L;
  @Mock
  private VehicleRepository vehicleRepository;
  @Mock
  private Authentication authentication;
  @Mock
  private SecurityContext securityContext;
  @InjectMocks
  private VehicleCommandServiceImpl vehicleCommandService;
  private UserDetailsImpl userDetails;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    // Configura el contexto de seguridad simulado
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
    userDetails = new UserDetailsImpl(USER_ID, "testuser", "test@example.com", authorities);
  }

  /**
   * Test handle create vehicle command.
   */
  @Test
  @DisplayName("Debería crear un vehículo correctamente cuando el usuario es vendedor")
  void testHandleCreateVehicleCommand() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetails);

      CreateVehicleCommand command = createSampleCommand();
      Vehicle vehicle = new Vehicle(command);
      vehicle.setProfileId(USER_ID);

      when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

      // Act
      Optional<Vehicle> result = vehicleCommandService.handle(command);

      // Assert
      assertTrue(result.isPresent());
      assertEquals(USER_ID, result.get().getProfileId());
      assertEquals(command.name(), result.get().getName());
      assertEquals(vehicleStatus.PENDING, result.get().getStatus());

      verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }
  }

  /**
   * Test handle create vehicle command non seller user.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando un usuario no vendedor intenta crear un vehículo")
  void testHandleCreateVehicleCommandNonSellerUser() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      Collection<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // No es vendedor
      UserDetailsImpl nonSellerUser =
          new UserDetailsImpl(USER_ID, "testuser", "test@example.com", authorities);

      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(nonSellerUser);

      CreateVehicleCommand command = createSampleCommand();

      // Act & Assert
      assertThrows(IllegalStateException.class, () -> vehicleCommandService.handle(command));

      verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
  }

  /**
   * Test handle update vehicle command.
   */
  @Test
  @DisplayName("Debería actualizar un vehículo correctamente cuando el usuario es propietario")
  void testHandleUpdateVehicleCommand() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetails);

      int vehicleId = 1;
      UpdateVehicleCommand updateCommand = createSampleUpdateCommand();

      Vehicle existingVehicle = new Vehicle(createSampleCommand());
      existingVehicle.setProfileId(USER_ID);

      Vehicle updatedVehicle = new Vehicle(createSampleCommand());
      updatedVehicle.updateVehicleInfo(updateCommand);
      updatedVehicle.setProfileId(USER_ID);

      when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(existingVehicle));
      when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

      // Act
      Optional<Vehicle> result = vehicleCommandService.handle(updateCommand, vehicleId);

      // Assert
      assertTrue(result.isPresent());
      assertEquals(updateCommand.name(), result.get().getName());
      assertEquals(updateCommand.status(), result.get().getStatus());

      verify(vehicleRepository, times(1)).findById(vehicleId);
      verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }
  }

  /**
   * Test handle update vehicle command non existent vehicle.
   */
  @Test
  @DisplayName("Debería lanzar excepción al actualizar un vehículo que no existe")
  void testHandleUpdateVehicleCommandNonExistentVehicle() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetails);

      int vehicleId = 999; // ID no existente
      UpdateVehicleCommand updateCommand = createSampleUpdateCommand();

      when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

      // Act & Assert
      assertThrows(IllegalStateException.class,
          () -> vehicleCommandService.handle(updateCommand, vehicleId));

      verify(vehicleRepository, times(1)).findById(vehicleId);
      verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
  }

  /**
   * Test handle update vehicle command unauthorized.
   */
  @Test
  @DisplayName("Debería lanzar excepción al actualizar un vehículo que pertenece a otro usuario")
  void testHandleUpdateVehicleCommandUnauthorized() {
    // Arrange
    try (MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(
        SecurityContextHolder.class)) {
      securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetails);

      int vehicleId = 1;
      UpdateVehicleCommand updateCommand = createSampleUpdateCommand();

      Vehicle existingVehicle = new Vehicle(createSampleCommand());
      existingVehicle.setProfileId(2L); // Otro usuario

      when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(existingVehicle));

      // Act & Assert
      assertThrows(IllegalStateException.class,
          () -> vehicleCommandService.handle(updateCommand, vehicleId));

      verify(vehicleRepository, times(1)).findById(vehicleId);
      verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
  }

  /**
   * Test delete vehicle.
   */
  @Test
  @DisplayName("Debería eliminar un vehículo correctamente cuando el usuario es propietario")
  void testDeleteVehicle() {
    // Arrange
    int vehicleId = 1;
    Vehicle existingVehicle = new Vehicle(createSampleCommand());
    existingVehicle.setProfileId(USER_ID);

    when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(existingVehicle));
    doNothing().when(vehicleRepository).delete(existingVehicle);

    // Act
    assertDoesNotThrow(() -> vehicleCommandService.deleteVehicle(vehicleId, USER_ID));

    // Assert
    verify(vehicleRepository, times(1)).findById(vehicleId);
    verify(vehicleRepository, times(1)).delete(existingVehicle);
  }

  /**
   * Test delete vehicle non existent.
   */
  @Test
  @DisplayName("Debería lanzar excepción al eliminar un vehículo que no existe")
  void testDeleteVehicleNonExistent() {
    // Arrange
    int vehicleId = 999; // ID no existente

    when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(IllegalStateException.class,
        () -> vehicleCommandService.deleteVehicle(vehicleId, USER_ID));

    verify(vehicleRepository, times(1)).findById(vehicleId);
    verify(vehicleRepository, never()).delete(any(Vehicle.class));
  }

  /**
   * Test delete vehicle unauthorized.
   */
  @Test
  @DisplayName("Debería lanzar excepción al eliminar un vehículo que pertenece a otro usuario")
  void testDeleteVehicleUnauthorized() {
    // Arrange
    int vehicleId = 1;
    Vehicle existingVehicle = new Vehicle(createSampleCommand());
    existingVehicle.setProfileId(2L); // Otro usuario

    when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(existingVehicle));

    // Act & Assert
    assertThrows(IllegalStateException.class,
        () -> vehicleCommandService.deleteVehicle(vehicleId, USER_ID));

    verify(vehicleRepository, times(1)).findById(vehicleId);
    verify(vehicleRepository, never()).delete(any(Vehicle.class));
  }

  /**
   * Test find by id.
   */
  @Test
  @DisplayName("Debería encontrar un vehículo por ID")
  void testFindById() {
    // Arrange
    int vehicleId = 1;
    Vehicle vehicle = new Vehicle(createSampleCommand());

    when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));

    // Act
    Optional<Vehicle> result = vehicleCommandService.findById(vehicleId);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(vehicle, result.get());

    verify(vehicleRepository, times(1)).findById(vehicleId);
  }

  private CreateVehicleCommand createSampleCommand() {
    return new CreateVehicleCommand(
        "Test Vehicle",
        "123456789",
        "test@example.com",
        "Toyota",
        "Corolla",
        "Red",
        "2023",
        25000.0,
        "Automatic",
        "2.0L",
        5000.0,
        "4",
        "ABC-123",
        "Lima",
        "Test vehicle description",
        Arrays.asList("image1.jpg", "image2.jpg"),
        "Gasoline",
        180
    );
  }

  private UpdateVehicleCommand createSampleUpdateCommand() {
    return new UpdateVehicleCommand(
        "Updated Vehicle",
        "987654321",
        "updated@example.com",
        "Toyota",
        "Camry",
        "Blue",
        "2023",
        30000.0,
        "Automatic",
        "2.5L",
        10000.0,
        "4",
        "XYZ-789",
        "Lima",
        "Updated vehicle description",
        Arrays.asList("image3.jpg", "image4.jpg"),
        "Gasoline",
        200,
        vehicleStatus.REVIEWED
    );
  }
} 