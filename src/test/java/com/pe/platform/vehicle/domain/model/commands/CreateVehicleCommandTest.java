package com.pe.platform.vehicle.domain.model.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The type Create vehicle command test.
 */
class CreateVehicleCommandTest {

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

  /**
   * Test create command with valid data.
   */
  @Test
  @DisplayName("Should create command with valid data")
  void testCreateCommandWithValidData() {
    // Act & Assert: Create command and verify no exceptions are thrown
    assertDoesNotThrow(() -> new CreateVehicleCommand(
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
        new CreateVehicleCommand(
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
            VALID_SPEED
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
        new CreateVehicleCommand(
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
            VALID_SPEED
        )
    );

    assertEquals("Price must be greater than zero", exceptionNegative.getMessage());

    // Act & Assert: Zero price
    IllegalArgumentException exceptionZero = assertThrows(IllegalArgumentException.class, () ->
        new CreateVehicleCommand(
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
            VALID_SPEED
        )
    );

    assertEquals("Price must be greater than zero", exceptionZero.getMessage());
  }

  /**
   * Test mileage zero or negative.
   */
  @Test
  @DisplayName("Should throw exception when mileage is zero or negative")
  void testMileageZeroOrNegative() {
    // Act & Assert: Negative mileage
    IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class, () ->
        new CreateVehicleCommand(
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
            VALID_SPEED
        )
    );

    assertEquals("Mileage must be greater than zero", exceptionNegative.getMessage());

    // Act & Assert: Zero mileage
    IllegalArgumentException exceptionZero = assertThrows(IllegalArgumentException.class, () ->
        new CreateVehicleCommand(
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
            VALID_SPEED
        )
    );

    assertEquals("Mileage must be greater than zero", exceptionZero.getMessage());
  }

  /**
   * Test speed zero or negative.
   */
  @Test
  @DisplayName("Should throw exception when speed is zero or negative")
  void testSpeedZeroOrNegative() {
    // Act & Assert: Negative speed
    IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class, () ->
        new CreateVehicleCommand(
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
            -1
        )
    );

    assertEquals("Speed must be greater than zero", exceptionNegative.getMessage());

    // Act & Assert: Zero speed
    IllegalArgumentException exceptionZero = assertThrows(IllegalArgumentException.class, () ->
        new CreateVehicleCommand(
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
            0
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
        new CreateVehicleCommand(
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
            VALID_SPEED
        )
    );

    assertEquals("At least one image is required", exceptionEmpty.getMessage());

    // Act & Assert: Null list
    IllegalArgumentException exceptionNull = assertThrows(IllegalArgumentException.class, () ->
        new CreateVehicleCommand(
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
            VALID_SPEED
        )
    );

    assertEquals("At least one image is required", exceptionNull.getMessage());
  }

  /**
   * Test command values accessible.
   */
  @Test
  @DisplayName("Should create command with valid values and retrieve them correctly")
  void testCommandValuesAccessible() {
    // Arrange & Act
    CreateVehicleCommand command = new CreateVehicleCommand(
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
  }
} 