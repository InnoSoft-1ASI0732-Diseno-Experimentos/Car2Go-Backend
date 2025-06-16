package com.pe.platform.vehicle.application.internal.services.queryservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByLocationQuery;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByProfileId;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleQuery;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleByIdQuery;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleIdByProfileId;
import com.pe.platform.vehicle.domain.model.queries.PutVehicleById;
import com.pe.platform.vehicle.infrastructure.persistence.jpa.VehicleRepository;
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
 * The type Vehicle query service impl test.
 */
@ExtendWith(MockitoExtension.class)
class VehicleQueryServiceImplTest {

  private final int VEHICLE_ID = 1;
  private final long PROFILE_ID = 1L;
  private final String LOCATION = "Lima";
  @Mock
  private VehicleRepository vehicleRepository;
  @InjectMocks
  private VehicleQueryServiceImpl vehicleQueryService;
  private Vehicle sampleVehicle1;
  private Vehicle sampleVehicle2;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    // Crear vehículos de muestra para pruebas
    sampleVehicle1 = createSampleVehicle(VEHICLE_ID, PROFILE_ID, LOCATION);
    sampleVehicle2 = createSampleVehicle(2, PROFILE_ID, LOCATION);
  }

  /**
   * Test handle get vehicle by id query.
   */
  @Test
  @DisplayName("Debería obtener un vehículo por ID")
  void testHandleGetVehicleByIdQuery() {
    // Arrange
    GetVehicleByIdQuery query = new GetVehicleByIdQuery(VEHICLE_ID);
    when(vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.of(sampleVehicle1));

    // Act
    Optional<Vehicle> result = vehicleQueryService.handle(query);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(sampleVehicle1, result.get());
    assertEquals(VEHICLE_ID, result.get().getId());
    assertEquals(PROFILE_ID, result.get().getProfileId());

    verify(vehicleRepository, times(1)).findById(VEHICLE_ID);
  }

  /**
   * Test handle get vehicle by id query not found.
   */
  @Test
  @DisplayName("Debería retornar Optional vacío cuando no existe vehículo con el ID")
  void testHandleGetVehicleByIdQueryNotFound() {
    // Arrange
    int nonExistentId = 999;
    GetVehicleByIdQuery query = new GetVehicleByIdQuery(nonExistentId);
    when(vehicleRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    // Act
    Optional<Vehicle> result = vehicleQueryService.handle(query);

    // Assert
    assertFalse(result.isPresent());

    verify(vehicleRepository, times(1)).findById(nonExistentId);
  }

  /**
   * Test handle get all vehicle by location query.
   */
  @Test
  @DisplayName("Debería obtener vehículos por ubicación")
  void testHandleGetAllVehicleByLocationQuery() {
    // Arrange
    List<Vehicle> expectedVehicles = Arrays.asList(sampleVehicle1, sampleVehicle2);
    GetAllVehicleByLocationQuery query = new GetAllVehicleByLocationQuery(LOCATION);
    when(vehicleRepository.findAllByLocation(LOCATION)).thenReturn(expectedVehicles);

    // Act
    List<Vehicle> result = vehicleQueryService.handle(query);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(expectedVehicles, result);
    assertEquals(LOCATION, result.get(0).getLocation());
    assertEquals(LOCATION, result.get(1).getLocation());

    verify(vehicleRepository, times(1)).findAllByLocation(LOCATION);
  }

  /**
   * Test handle get all vehicle by location query empty.
   */
  @Test
  @DisplayName("Debería retornar lista vacía cuando no hay vehículos en la ubicación")
  void testHandleGetAllVehicleByLocationQueryEmpty() {
    // Arrange
    String emptyLocation = "Cusco";
    GetAllVehicleByLocationQuery query = new GetAllVehicleByLocationQuery(emptyLocation);
    when(vehicleRepository.findAllByLocation(emptyLocation)).thenReturn(Collections.emptyList());

    // Act
    List<Vehicle> result = vehicleQueryService.handle(query);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(vehicleRepository, times(1)).findAllByLocation(emptyLocation);
  }

  /**
   * Test handle get all vehicle query.
   */
  @Test
  @DisplayName("Debería obtener todos los vehículos")
  void testHandleGetAllVehicleQuery() {
    // Arrange
    List<Vehicle> expectedVehicles = Arrays.asList(sampleVehicle1, sampleVehicle2);
    GetAllVehicleQuery query = new GetAllVehicleQuery();
    when(vehicleRepository.findAll()).thenReturn(expectedVehicles);

    // Act
    List<Vehicle> result = vehicleQueryService.handle(query);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(expectedVehicles, result);

    verify(vehicleRepository, times(1)).findAll();
  }

  /**
   * Test handle get all vehicle query empty.
   */
  @Test
  @DisplayName("Debería retornar lista vacía cuando no hay vehículos")
  void testHandleGetAllVehicleQueryEmpty() {
    // Arrange
    GetAllVehicleQuery query = new GetAllVehicleQuery();
    when(vehicleRepository.findAll()).thenReturn(Collections.emptyList());

    // Act
    List<Vehicle> result = vehicleQueryService.handle(query);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(vehicleRepository, times(1)).findAll();
  }

  /**
   * Test handle put vehicle by id.
   */
  @Test
  @DisplayName("Debería actualizar un vehículo por ID")
  void testHandlePutVehicleById() {
    // Arrange
    PutVehicleById query = new PutVehicleById(VEHICLE_ID);
    when(vehicleRepository.updateById(VEHICLE_ID)).thenReturn(Optional.of(sampleVehicle1));

    // Act
    Optional<Vehicle> result = vehicleQueryService.handle(query);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(sampleVehicle1, result.get());

    verify(vehicleRepository, times(1)).updateById(VEHICLE_ID);
  }

  /**
   * Test handle get all vehicle by profile id.
   */
  @Test
  @DisplayName("Debería obtener vehículos por ID de perfil")
  void testHandleGetAllVehicleByProfileId() {
    // Arrange
    List<Vehicle> expectedVehicles = Arrays.asList(sampleVehicle1, sampleVehicle2);
    GetAllVehicleByProfileId query = new GetAllVehicleByProfileId(PROFILE_ID);
    when(vehicleRepository.findAllVehiclesByProfileId(PROFILE_ID)).thenReturn(expectedVehicles);

    // Act
    List<Vehicle> result = vehicleQueryService.handle(query);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(expectedVehicles, result);
    assertEquals(PROFILE_ID, result.get(0).getProfileId());
    assertEquals(PROFILE_ID, result.get(1).getProfileId());

    verify(vehicleRepository, times(1)).findAllVehiclesByProfileId(PROFILE_ID);
  }

  /**
   * Test handle get vehicle id by profile id.
   */
  @Test
  @DisplayName("Debería obtener vehículos por ID de perfil (método alternativo)")
  void testHandleGetVehicleIdByProfileId() {
    // Arrange
    List<Vehicle> expectedVehicles = Arrays.asList(sampleVehicle1, sampleVehicle2);
    GetVehicleIdByProfileId query = new GetVehicleIdByProfileId(PROFILE_ID);
    when(vehicleRepository.findByProfileId(PROFILE_ID)).thenReturn(expectedVehicles);

    // Act
    List<Vehicle> result = vehicleQueryService.handle(query);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(expectedVehicles, result);
    assertEquals(PROFILE_ID, result.get(0).getProfileId());
    assertEquals(PROFILE_ID, result.get(1).getProfileId());

    verify(vehicleRepository, times(1)).findByProfileId(PROFILE_ID);
  }

  /**
   * Crea un vehículo de muestra para pruebas
   */
  private Vehicle createSampleVehicle(int id, long profileId, String location) {
    CreateVehicleCommand command = new CreateVehicleCommand(
        "Test Vehicle " + id,
        "123456789",
        "test" + id + "@example.com",
        "Toyota",
        "Corolla",
        "Red",
        "2023",
        25000.0,
        "Automatic",
        "2.0L",
        5000.0,
        "4",
        "ABC-" + id,
        location,
        "Test vehicle description",
        Arrays.asList("image1.jpg", "image2.jpg"),
        "Gasoline",
        180
    );

    Vehicle vehicle = new Vehicle(command);
    vehicle.setProfileId(profileId);

    // Simular ID que normalmente sería establecido por JPA
    try {
      java.lang.reflect.Field idField = Vehicle.class.getDeclaredField("id");
      idField.setAccessible(true);
      idField.set(vehicle, id);
    } catch (Exception e) {
      throw new RuntimeException("Error setting vehicle ID: " + e.getMessage());
    }

    return vehicle;
  }
} 