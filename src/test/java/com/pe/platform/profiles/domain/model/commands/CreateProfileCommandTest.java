package com.pe.platform.profiles.domain.model.commands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateProfileCommandTest {

    @Test
    @DisplayName("Debería crear CreateProfileCommand correctamente")
    void testCreateCommand() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String image = "profile-image.jpg";
        String dni = "12345678";
        String address = "Calle Principal 123";
        String phone = "+51987654321";
        
        // Act
        CreateProfileCommand command = new CreateProfileCommand(
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
    
    @Test
    @DisplayName("Debería lanzar excepción cuando firstName es nulo")
    void testCreateCommandWithNullFirstName() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new CreateProfileCommand(null, "Doe", "email@example.com", "image.jpg", 
                "12345678", "Test Address", "+51987654321"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando lastName es nulo")
    void testCreateCommandWithNullLastName() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new CreateProfileCommand("John", null, "email@example.com", "image.jpg", 
                "12345678", "Test Address", "+51987654321"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando email es nulo")
    void testCreateCommandWithNullEmail() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new CreateProfileCommand("John", "Doe", null, "image.jpg", 
                "12345678", "Test Address", "+51987654321"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando image es nula")
    void testCreateCommandWithNullImage() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new CreateProfileCommand("John", "Doe", "email@example.com", null, 
                "12345678", "Test Address", "+51987654321"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando dni es nulo")
    void testCreateCommandWithNullDni() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new CreateProfileCommand("John", "Doe", "email@example.com", "image.jpg", 
                null, "Test Address", "+51987654321"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando address es nulo")
    void testCreateCommandWithNullAddress() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new CreateProfileCommand("John", "Doe", "email@example.com", "image.jpg", 
                "12345678", null, "+51987654321"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando phone es nulo")
    void testCreateCommandWithNullPhone() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new CreateProfileCommand("John", "Doe", "email@example.com", "image.jpg", 
                "12345678", "Test Address", null));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando firstName está vacío")
    void testCreateCommandWithEmptyFirstName() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new CreateProfileCommand("", "Doe", "email@example.com", "image.jpg", 
                "12345678", "Test Address", "+51987654321"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando firstName está en blanco")
    void testCreateCommandWithBlankFirstName() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new CreateProfileCommand("   ", "Doe", "email@example.com", "image.jpg", 
                "12345678", "Test Address", "+51987654321"));
    }
} 