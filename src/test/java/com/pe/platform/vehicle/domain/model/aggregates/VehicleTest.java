package com.pe.platform.vehicle.domain.model.aggregates;

import com.pe.platform.interaction.domain.model.aggregates.Review;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;
import com.pe.platform.vehicle.domain.model.valueobjects.vehicleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    // Test data
    private static final String VALID_NAME = "Test Vehicle";
    private static final String VALID_PHONE = "987654321";
    private static final String VALID_EMAIL = "test@example.com";
    private static final String VALID_BRAND = "Toyota";
    private static final String VALID_MODEL = "Corolla";
    private static final String VALID_COLOR = "Red";
    private static final String VALID_YEAR = "2023";
    private static final double VALID_PRICE = 25000.0;
    private static final String VALID_TRANSMISSION = "Automatic";
    private static final String VALID_ENGINE = "2.0L";
    private static final double VALID_MILEAGE = 5000.0;
    private static final String VALID_DOORS = "4";
    private static final String VALID_PLATE = "ABC-123";
    private static final String VALID_LOCATION = "Lima, Peru";
    private static final String VALID_DESCRIPTION = "Vehicle in excellent condition";
    private static final List<String> VALID_IMAGES = Arrays.asList("image1.jpg", "image2.jpg");
    private static final String VALID_FUEL = "Gasoline";
    private static final int VALID_SPEED = 180;

    // Updated data for tests
    private static final String UPDATED_NAME = "Updated Vehicle";
    private static final String UPDATED_PHONE = "123456789";
    private static final String UPDATED_EMAIL = "updated@example.com";
    private static final String UPDATED_MODEL = "Camry";
    private static final double UPDATED_PRICE = 30000.0;
    private static final String UPDATED_COLOR = "Blue";

    @Mock
    private List<Review> reviewsMock;

    private CreateVehicleCommand validCreateCommand;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Arrange: Prepare test data
        validCreateCommand = new CreateVehicleCommand(
            VALID_NAME, 
            VALID_PHONE, 
            VALID_EMAIL, 
            VALID_BRAND, 
            VALID_MODEL, 
            VALID_COLOR, 
            VALID_YEAR, 
            VALID_PRICE, 
            VALID_TRANSMISSION, 
            VALID_ENGINE, 
            VALID_MILEAGE, 
            VALID_DOORS, 
            VALID_PLATE, 
            VALID_LOCATION, 
            VALID_DESCRIPTION, 
            VALID_IMAGES, 
            VALID_FUEL, 
            VALID_SPEED
        );
    }

    @Test
    @DisplayName("Should create a vehicle correctly")
    void testVehicleCreation() {
        // Act: Create vehicle with command
        vehicle = new Vehicle(validCreateCommand);

        // Assert: Verify all fields are initialized correctly
        assertEquals(VALID_NAME, vehicle.getName());
        assertEquals(VALID_PHONE, vehicle.getPhone());
        assertEquals(VALID_EMAIL, vehicle.getEmail());
        assertEquals(VALID_BRAND, vehicle.getBrand());
        assertEquals(VALID_MODEL, vehicle.getModel());
        assertEquals(VALID_COLOR, vehicle.getColor());
        assertEquals(VALID_YEAR, vehicle.getYear());
        assertEquals(VALID_PRICE, vehicle.getPrice());
        assertEquals(VALID_TRANSMISSION, vehicle.getTransmission());
        assertEquals(VALID_ENGINE, vehicle.getEngine());
        assertEquals(VALID_MILEAGE, vehicle.getMileage());
        assertEquals(VALID_DOORS, vehicle.getDoors());
        assertEquals(VALID_PLATE, vehicle.getPlate());
        assertEquals(VALID_LOCATION, vehicle.getLocation());
        assertEquals(VALID_DESCRIPTION, vehicle.getDescription());
        assertEquals(VALID_IMAGES, vehicle.getImages());
        assertEquals(VALID_FUEL, vehicle.getFuel());
        assertEquals(VALID_SPEED, vehicle.getSpeed());
        assertEquals(vehicleStatus.PENDING, vehicle.getStatus());
    }

    @Test
    @DisplayName("Should update a vehicle correctly")
    void testVehicleUpdate() {
        // Arrange: Create vehicle and update command
        vehicle = new Vehicle(validCreateCommand);
        UpdateVehicleCommand updateCommand = new UpdateVehicleCommand(
            UPDATED_NAME,
            UPDATED_PHONE,
            UPDATED_EMAIL,
            VALID_BRAND,
            UPDATED_MODEL,
            UPDATED_COLOR,
            VALID_YEAR,
            UPDATED_PRICE,
            VALID_TRANSMISSION,
            VALID_ENGINE,
            VALID_MILEAGE,
            VALID_DOORS,
            VALID_PLATE,
            VALID_LOCATION,
            VALID_DESCRIPTION,
            VALID_IMAGES,
            VALID_FUEL,
            VALID_SPEED,
            vehicleStatus.PENDING
        );
        
        // Act: Update vehicle
        vehicle.updateVehicleInfo(updateCommand);
        
        // Assert: Verify fields were updated correctly
        assertEquals(UPDATED_NAME, vehicle.getName());
        assertEquals(UPDATED_PHONE, vehicle.getPhone());
        assertEquals(UPDATED_EMAIL, vehicle.getEmail());
        assertEquals(UPDATED_MODEL, vehicle.getModel());
        assertEquals(UPDATED_COLOR, vehicle.getColor());
        assertEquals(UPDATED_PRICE, vehicle.getPrice());
        assertEquals(vehicleStatus.PENDING, vehicle.getStatus());
    }

    @Test
    @DisplayName("Should update vehicle status correctly")
    void testStatusUpdate() {
        // Arrange: Create vehicle and status update command
        vehicle = new Vehicle(validCreateCommand);
        UpdateVehicleCommand statusUpdateCommand = new UpdateVehicleCommand(
            VALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            VALID_BRAND,
            VALID_MODEL,
            VALID_COLOR,
            VALID_YEAR,
            VALID_PRICE,
            VALID_TRANSMISSION,
            VALID_ENGINE,
            VALID_MILEAGE,
            VALID_DOORS,
            VALID_PLATE,
            VALID_LOCATION,
            VALID_DESCRIPTION,
            VALID_IMAGES,
            VALID_FUEL,
            VALID_SPEED,
            vehicleStatus.REVIEWED
        );
        
        // Act: Update vehicle status
        vehicle.updateVehicleInfo(statusUpdateCommand);
        
        // Assert: Verify status was updated correctly
        assertEquals(vehicleStatus.REVIEWED, vehicle.getStatus());
    }

    @Test
    @DisplayName("Should set and get profileId correctly")
    void testSetAndGetProfileId() {
        // Arrange: Create vehicle
        vehicle = new Vehicle(validCreateCommand);
        long profileId = 12345L;
        
        // Act: Set profileId
        vehicle.setProfileId(profileId);
        
        // Assert: Verify profileId was set correctly
        assertEquals(profileId, vehicle.getProfileId());
        assertEquals(profileId, vehicle.getUserId());
    }

    @Test
    @DisplayName("Should maintain PENDING status by default when creating a vehicle")
    void testDefaultStatusIsPending() {
        // Act: Create vehicle
        vehicle = new Vehicle(validCreateCommand);
        
        // Assert: Verify initial status is PENDING
        assertEquals(vehicleStatus.PENDING, vehicle.getStatus());
    }

    @Test
    @DisplayName("Should reject a vehicle correctly")
    void testRejectVehicle() {
        // Arrange: Create vehicle and reject command
        vehicle = new Vehicle(validCreateCommand);
        UpdateVehicleCommand rejectCommand = new UpdateVehicleCommand(
            VALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            VALID_BRAND,
            VALID_MODEL,
            VALID_COLOR,
            VALID_YEAR,
            VALID_PRICE,
            VALID_TRANSMISSION,
            VALID_ENGINE,
            VALID_MILEAGE,
            VALID_DOORS,
            VALID_PLATE,
            VALID_LOCATION,
            VALID_DESCRIPTION,
            VALID_IMAGES,
            VALID_FUEL,
            VALID_SPEED,
            vehicleStatus.REJECT
        );
        
        // Act: Reject vehicle
        vehicle.updateVehicleInfo(rejectCommand);
        
        // Assert: Verify status was updated to REJECT
        assertEquals(vehicleStatus.REJECT, vehicle.getStatus());
    }
}