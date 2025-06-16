package com.pe.platform.vehicle.interfaces.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByLocationQuery;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByProfileId;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleQuery;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleByIdQuery;
import com.pe.platform.vehicle.domain.model.valueobjects.vehicleStatus;
import com.pe.platform.vehicle.domain.services.VehicleCommandService;
import com.pe.platform.vehicle.domain.services.VehicleQueryService;
import com.pe.platform.vehicle.interfaces.rest.resources.CreateVehicleResource;
import com.pe.platform.vehicle.interfaces.rest.resources.UpdateVehicleResource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * The type Vehicle controller test.
 */
class VehicleControllerTest {

  private MockMvc mockMvc;

  @Mock
  private VehicleCommandService vehicleCommandService;

  @Mock
  private VehicleQueryService vehicleQueryService;

  @Mock
  private Authentication authentication;

  @Mock
  private SecurityContext securityContext;

  @InjectMocks
  private VehicleController vehicleController;

  private ObjectMapper objectMapper;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
    objectMapper = new ObjectMapper();

    // Configure security context
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    // Create user details with ROLE_SELLER authority
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
    UserDetailsImpl userDetails =
        new UserDetailsImpl(1L, "testuser", "test@example.com", authorities);
    when(authentication.getPrincipal()).thenReturn(userDetails);
  }

  // Helper method to create a sample vehicle
  private Vehicle createSampleVehicle() {
    Vehicle vehicle = new Vehicle();
    vehicle.setId(1);
    vehicle.setName("Test Vehicle");
    vehicle.setPhone("123456789");
    vehicle.setEmail("test@example.com");
    vehicle.setBrand("Toyota");
    vehicle.setModel("Corolla");
    vehicle.setColor("Red");
    vehicle.setYear("2023");
    vehicle.setPrice(25000.0);
    vehicle.setTransmission("Automatic");
    vehicle.setEngine("2.0L");
    vehicle.setMileage(5000.0);
    vehicle.setDoors("4");
    vehicle.setPlate("ABC-123");
    vehicle.setLocation("Lima");
    vehicle.setDescription("Test vehicle description");
    vehicle.setImages(Arrays.asList("image1.jpg", "image2.jpg"));
    vehicle.setFuel("Gasoline");
    vehicle.setSpeed(180);
    vehicle.setStatus(vehicleStatus.PENDING);
    vehicle.setProfileId(1L);
    vehicle.setCreatedDate(LocalDateTime.now());
    vehicle.setLastModifiedDate(LocalDateTime.now());
    return vehicle;
  }

  // Helper method to create a sample create resource
  private CreateVehicleResource createSampleCreateResource() {
    return new CreateVehicleResource(
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
        5000,
        "4",
        "ABC-123",
        "Lima",
        "Test vehicle description",
        Arrays.asList("image1.jpg", "image2.jpg"),
        "Gasoline",
        180
    );
  }

  // Helper method to create a sample update resource
  private UpdateVehicleResource createSampleUpdateResource() {
    return new UpdateVehicleResource(
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
        10000,
        "4",
        "XYZ-789",
        "Cusco",
        "Updated vehicle description",
        Arrays.asList("image3.jpg", "image4.jpg"),
        "Gasoline",
        200,
        "PENDING"
    );
  }

  /**
   * Test create vehicle.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should create a vehicle when valid data is provided")
  void testCreateVehicle() throws Exception {
    // Arrange
    CreateVehicleResource resource = createSampleCreateResource();
    Vehicle vehicle = createSampleVehicle();

    when(vehicleCommandService.handle(any(CreateVehicleCommand.class))).thenReturn(
        Optional.of(vehicle));

    // Act & Assert
    mockMvc.perform(post("/api/v1/vehicle")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(resource)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Test Vehicle"))
        .andExpect(jsonPath("$.status").value("PENDING"));

    verify(vehicleCommandService, times(1)).handle(any(CreateVehicleCommand.class));
  }

  /**
   * Test create vehicle fails.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should return 400 when vehicle creation fails")
  void testCreateVehicleFails() throws Exception {
    // Arrange
    CreateVehicleResource resource = createSampleCreateResource();

    when(vehicleCommandService.handle(any(CreateVehicleCommand.class))).thenReturn(
        Optional.empty());

    // Act & Assert
    mockMvc.perform(post("/api/v1/vehicle")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(resource)))
        .andExpect(status().isBadRequest());

    verify(vehicleCommandService, times(1)).handle(any(CreateVehicleCommand.class));
  }

  /**
   * Test get vehicle by id.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should get a vehicle by ID when it exists")
  void testGetVehicleById() throws Exception {
    // Arrange
    Vehicle vehicle = createSampleVehicle();

    when(vehicleQueryService.handle(any(GetVehicleByIdQuery.class))).thenReturn(
        Optional.of(vehicle));

    // Act & Assert
    mockMvc.perform(get("/api/v1/vehicle/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Test Vehicle"));

    verify(vehicleQueryService, times(1)).handle(any(GetVehicleByIdQuery.class));
  }

  /**
   * Test get vehicle by id not found.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should return 404 when vehicle with given ID does not exist")
  void testGetVehicleByIdNotFound() throws Exception {
    // Arrange
    when(vehicleQueryService.handle(any(GetVehicleByIdQuery.class))).thenReturn(Optional.empty());

    // Act & Assert
    mockMvc.perform(get("/api/v1/vehicle/999"))
        .andExpect(status().isNotFound());

    verify(vehicleQueryService, times(1)).handle(any(GetVehicleByIdQuery.class));
  }

  /**
   * Test get all by location.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should get all vehicles by location")
  void testGetAllByLocation() throws Exception {
    // Arrange
    Vehicle vehicle1 = createSampleVehicle();
    Vehicle vehicle2 = createSampleVehicle();
    vehicle2.setId(2);

    List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

    when(vehicleQueryService.handle(any(GetAllVehicleByLocationQuery.class))).thenReturn(vehicles);

    // Act & Assert
    mockMvc.perform(get("/api/v1/vehicle/location/Lima"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2));

    verify(vehicleQueryService, times(1)).handle(any(GetAllVehicleByLocationQuery.class));
  }

  /**
   * Test get all by location empty.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should return 404 when no vehicles found in location")
  void testGetAllByLocationEmpty() throws Exception {
    // Arrange
    when(vehicleQueryService.handle(any(GetAllVehicleByLocationQuery.class))).thenReturn(
        Collections.emptyList());

    // Act & Assert
    mockMvc.perform(get("/api/v1/vehicle/location/Unknown"))
        .andExpect(status().isNotFound());

    verify(vehicleQueryService, times(1)).handle(any(GetAllVehicleByLocationQuery.class));
  }

  /**
   * Test get all vehicles.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should get all vehicles")
  void testGetAllVehicles() throws Exception {
    // Arrange
    Vehicle vehicle1 = createSampleVehicle();
    Vehicle vehicle2 = createSampleVehicle();
    vehicle2.setId(2);

    List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

    when(vehicleQueryService.handle(any(GetAllVehicleQuery.class))).thenReturn(vehicles);

    // Act & Assert
    mockMvc.perform(get("/api/v1/vehicle/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2));

    verify(vehicleQueryService, times(1)).handle(any(GetAllVehicleQuery.class));
  }

  /**
   * Test update vehicle.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should update a vehicle when valid data is provided")
  void testUpdateVehicle() throws Exception {
    // Arrange
    UpdateVehicleResource resource = createSampleUpdateResource();
    Vehicle updatedVehicle = createSampleVehicle();
    updatedVehicle.setName("Updated Vehicle");
    updatedVehicle.setLocation("Cusco");

    when(vehicleCommandService.handle(any(UpdateVehicleCommand.class), anyInt())).thenReturn(
        Optional.of(updatedVehicle));

    // Act & Assert
    mockMvc.perform(put("/api/v1/vehicle/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(resource)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Updated Vehicle"))
        .andExpect(jsonPath("$.location").value("Cusco"));

    verify(vehicleCommandService, times(1)).handle(any(UpdateVehicleCommand.class), anyInt());
  }

  /**
   * Test update vehicle not found.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should return 404 when updating non-existent vehicle")
  void testUpdateVehicleNotFound() throws Exception {
    // Arrange
    UpdateVehicleResource resource = createSampleUpdateResource();

    when(vehicleCommandService.handle(any(UpdateVehicleCommand.class), anyInt())).thenReturn(
        Optional.empty());

    // Act & Assert
    mockMvc.perform(put("/api/v1/vehicle/999")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(resource)))
        .andExpect(status().isNotFound());

    verify(vehicleCommandService, times(1)).handle(any(UpdateVehicleCommand.class), anyInt());
  }

  /**
   * Test get all vehicles by profile id.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should get all vehicles by profile ID")
  void testGetAllVehiclesByProfileId() throws Exception {
    // Arrange
    Vehicle vehicle1 = createSampleVehicle();
    Vehicle vehicle2 = createSampleVehicle();
    vehicle2.setId(2);

    List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

    when(vehicleQueryService.handle(any(GetAllVehicleByProfileId.class))).thenReturn(vehicles);

    // Act & Assert
    mockMvc.perform(get("/api/v1/vehicle/all/vehicles/profile/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2));

    verify(vehicleQueryService, times(1)).handle(any(GetAllVehicleByProfileId.class));
  }

  /**
   * Test delete vehicle.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should delete a vehicle")
  void testDeleteVehicle() throws Exception {
    // Arrange
    doNothing().when(vehicleCommandService).deleteVehicle(anyInt(), anyLong());

    // Act & Assert
    mockMvc.perform(delete("/api/v1/vehicle/1"))
        .andExpect(status().isNoContent());

    verify(vehicleCommandService, times(1)).deleteVehicle(anyInt(), anyLong());
  }

  /**
   * Test delete vehicle unauthorized.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Should return 403 when attempting to delete unauthorized vehicle")
  void testDeleteVehicleUnauthorized() throws Exception {
    // Arrange
    doThrow(new IllegalStateException("Not authorized to delete this vehicle"))
        .when(vehicleCommandService).deleteVehicle(anyInt(), anyLong());

    // Act & Assert
    mockMvc.perform(delete("/api/v1/vehicle/1"))
        .andExpect(status().isForbidden());

    verify(vehicleCommandService, times(1)).deleteVehicle(anyInt(), anyLong());
  }
}