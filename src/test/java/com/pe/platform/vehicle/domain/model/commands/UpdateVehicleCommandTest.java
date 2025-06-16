package com.pe.platform.vehicle.domain.model.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pe.platform.vehicle.domain.model.valueobjects.vehicleStatus;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The type Update vehicle command test.
 */
class UpdateVehicleCommandTest {

  // Valid test data
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
  private static final vehicleStatus VALID_STATUS = vehicleStatus.PENDING;

  /**
   * Test create update command with valid data.
   */
  @Test
  @DisplayName("Should create update command with valid data")
  void testCreateUpdateCommandWithValidData() {
    // Act & Assert: Create command and verify no exceptions are thrown
    assertDoesNotThrow(() -> new UpdateVehicleCommand(
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
        VALID_STATUS
    ));
  }

  /**
   * Test name null or empty.
   *
   * @param invalidName the invalid name
   */
  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" ", "  "})
  @DisplayName("Should throw exception when name is null or empty")
  void testNameNullOrEmpty(String invalidName) {
    // Arrange, Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        new UpdateVehicleCommand(
            invalidName,
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
            VALID_STATUS
        )
    );

    assertEquals("Name cannot be null or empty", exception.getMessage());
  }

  /**
   * Test price zero or negative.
   */
  @Test
  @DisplayName("Should throw exception when price is zero or negative")
  void testPriceZeroOrNegative() {
    // Act & Assert: Negative price
    IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class, () ->
        new UpdateVehicleCommand(
            VALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            VALID_BRAND,
            VALID_MODEL,
            VALID_COLOR,
            VALID_YEAR,
            -1.0,
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
            VALID_STATUS
        )
    );

    assertEquals("Price must be greater than zero", exceptionNegative.getMessage());

    // Act & Assert: Zero price
    IllegalArgumentException exceptionZero = assertThrows(IllegalArgumentException.class, () ->
        new UpdateVehicleCommand(
            VALID_NAME,
            VALID_PHONE,
            VALID_EMAIL,
            VALID_BRAND,
            VALID_MODEL,
            VALID_COLOR,
            VALID_YEAR,
            0.0,
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
            VALID_STATUS
        )
    );

    assertEquals("Price must be greater than zero", exceptionZero.getMessage());
  }

  /**
   * Test mileage negative.
   */
  @Test
  @DisplayName("Should throw exception when mileage is negative")
  void testMileageNegative() {
    // Act & Assert: Negative mileage
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        new UpdateVehicleCommand(
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
            -1.0,
            VALID_DOORS,
            VALID_PLATE,
            VALID_LOCATION,
            VALID_DESCRIPTION,
            VALID_IMAGES,
            VALID_FUEL,
            VALID_SPEED,
            VALID_STATUS
        )
    );

    assertEquals("Mileage cannot be negative", exception.getMessage());
  }

  /**
   * Test mileage zero.
   */
  @Test
  @DisplayName("Should allow zero mileage in update operation")
  void testMileageZero() {
    // Act & Assert: Zero mileage should be valid for update
    assertDoesNotThrow(() ->
        new UpdateVehicleCommand(
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
            0.0,
            VALID_DOORS,
            VALID_PLATE,
            VALID_LOCATION,
            VALID_DESCRIPTION,
            VALID_IMAGES,
            VALID_FUEL,
            VALID_SPEED,
            VALID_STATUS
        )
    );
  }

  /**
   * Test speed zero or negative.
   */
  @Test
  @DisplayName("Should throw exception when speed is zero or negative")
  void testSpeedZeroOrNegative() {
    // Act & Assert: Negative speed
    IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class, () ->
        new UpdateVehicleCommand(
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
            -1,
            VALID_STATUS
        )
    );

    assertEquals("Speed must be greater than zero", exceptionNegative.getMessage());

    // Act & Assert: Zero speed
    IllegalArgumentException exceptionZero = assertThrows(IllegalArgumentException.class, () ->
        new UpdateVehicleCommand(
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
            0,
            VALID_STATUS
        )
    );

    assertEquals("Speed must be greater than zero", exceptionZero.getMessage());
  }

  /**
   * Test images null or empty.
   */
  @Test
  @DisplayName("Should throw exception when images list is null or empty")
  void testImagesNullOrEmpty() {
    // Act & Assert: Empty list
    IllegalArgumentException exceptionEmpty = assertThrows(IllegalArgumentException.class, () ->
        new UpdateVehicleCommand(
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
            Collections.emptyList(),
            VALID_FUEL,
            VALID_SPEED,
            VALID_STATUS
        )
    );

    assertEquals("At least one image is required", exceptionEmpty.getMessage());

    // Act & Assert: Null list
    IllegalArgumentException exceptionNull = assertThrows(IllegalArgumentException.class, () ->
        new UpdateVehicleCommand(
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
            null,
            VALID_FUEL,
            VALID_SPEED,
            VALID_STATUS
        )
    );

    assertEquals("At least one image is required", exceptionNull.getMessage());
  }

  /**
   * Test status null.
   */
  @Test
  @DisplayName("Should throw exception when status is null")
  void testStatusNull() {
    // Act & Assert: Null status
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        new UpdateVehicleCommand(
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
            null
        )
    );

    assertEquals("Status cannot be null", exception.getMessage());
  }

  /**
   * Test different statuses.
   */
  @Test
  @DisplayName("Should allow changing between different statuses")
  void testDifferentStatuses() {
    // Act & Assert: PENDING status
    assertDoesNotThrow(() ->
        new UpdateVehicleCommand(
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
            vehicleStatus.PENDING
        )
    );

    // Act & Assert: REVIEWED status
    assertDoesNotThrow(() ->
        new UpdateVehicleCommand(
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
        )
    );

    // Act & Assert: REJECT status
    assertDoesNotThrow(() ->
        new UpdateVehicleCommand(
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
        )
    );
  }

  /**
   * Test command values accessible.
   */
  @Test
  @DisplayName("Should create command with valid values and retrieve them correctly")
  void testCommandValuesAccessible() {
    // Arrange & Act
    UpdateVehicleCommand command = new UpdateVehicleCommand(
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
        VALID_STATUS
    );

    // Assert: Verify all values are accessible correctly
    assertEquals(VALID_NAME, command.name());
    assertEquals(VALID_PHONE, command.phone());
    assertEquals(VALID_EMAIL, command.email());
    assertEquals(VALID_BRAND, command.brand());
    assertEquals(VALID_MODEL, command.model());
    assertEquals(VALID_COLOR, command.color());
    assertEquals(VALID_YEAR, command.year());
    assertEquals(VALID_PRICE, command.price());
    assertEquals(VALID_TRANSMISSION, command.transmission());
    assertEquals(VALID_ENGINE, command.engine());
    assertEquals(VALID_MILEAGE, command.mileage());
    assertEquals(VALID_DOORS, command.doors());
    assertEquals(VALID_PLATE, command.plate());
    assertEquals(VALID_LOCATION, command.location());
    assertEquals(VALID_DESCRIPTION, command.description());
    assertEquals(VALID_IMAGES, command.images());
    assertEquals(VALID_FUEL, command.fuel());
    assertEquals(VALID_SPEED, command.speed());
    assertEquals(VALID_STATUS, command.status());
  }
} 