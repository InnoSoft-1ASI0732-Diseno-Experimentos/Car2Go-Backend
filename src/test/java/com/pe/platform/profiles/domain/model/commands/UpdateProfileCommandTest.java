package com.pe.platform.profiles.domain.model.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The type Update profile command test.
 */
class UpdateProfileCommandTest {

  /**
   * Test update command.
   */
  @Test
  @DisplayName("Debería crear UpdateProfileCommand correctamente")
  void testUpdateCommand() {
    // Arrange
    String firstName = "Jane";
    String lastName = "Smith";
    String email = "jane.smith@example.com";
    String image = "new-profile-image.jpg";
    String dni = "87654321";
    String address = "Avenida Secundaria 456";
    String phone = "+51123456789";

    // Act
    UpdateProfileCommand command = new UpdateProfileCommand(
        firstName, lastName, email, image, dni, address, phone);

    // Assert
    assertEquals(firstName, command.firstName());
    assertEquals(lastName, command.lastName());
    assertEquals(email, command.email());
    assertEquals(image, command.image());
    assertEquals(dni, command.dni());
    assertEquals(address, command.address());
    assertEquals(phone, command.phone());
  }

  /**
   * Test update command with null first name.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando firstName es nulo")
  void testUpdateCommandWithNullFirstName() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
        new UpdateProfileCommand(null, "Smith", "email@example.com", "image.jpg",
            "87654321", "Test Address", "+51123456789"));
  }

  /**
   * Test update command with null last name.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando lastName es nulo")
  void testUpdateCommandWithNullLastName() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
        new UpdateProfileCommand("Jane", null, "email@example.com", "image.jpg",
            "87654321", "Test Address", "+51123456789"));
  }

  /**
   * Test update command with null email.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando email es nulo")
  void testUpdateCommandWithNullEmail() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
        new UpdateProfileCommand("Jane", "Smith", null, "image.jpg",
            "87654321", "Test Address", "+51123456789"));
  }

  /**
   * Test update command with null image.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando image es nula")
  void testUpdateCommandWithNullImage() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
        new UpdateProfileCommand("Jane", "Smith", "email@example.com", null,
            "87654321", "Test Address", "+51123456789"));
  }

  /**
   * Test update command with null dni.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando dni es nulo")
  void testUpdateCommandWithNullDni() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
        new UpdateProfileCommand("Jane", "Smith", "email@example.com", "image.jpg",
            null, "Test Address", "+51123456789"));
  }

  /**
   * Test update command with null address.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando address es nulo")
  void testUpdateCommandWithNullAddress() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
        new UpdateProfileCommand("Jane", "Smith", "email@example.com", "image.jpg",
            "87654321", null, "+51123456789"));
  }

  /**
   * Test update command with null phone.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando phone es nulo")
  void testUpdateCommandWithNullPhone() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
        new UpdateProfileCommand("Jane", "Smith", "email@example.com", "image.jpg",
            "87654321", "Test Address", null));
  }

  /**
   * Test update command with empty first name.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando firstName está vacío")
  void testUpdateCommandWithEmptyFirstName() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
        new UpdateProfileCommand("", "Smith", "email@example.com", "image.jpg",
            "87654321", "Test Address", "+51123456789"));
  }

  /**
   * Test update command with empty last name.
   */
  @Test
  @DisplayName("Debería lanzar excepción cuando lastName está vacío")
  void testUpdateCommandWithEmptyLastName() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
        new UpdateProfileCommand("Jane", "", "email@example.com", "image.jpg",
            "87654321", "Test Address", "+51123456789"));
  }
} 