package com.pe.platform.profile.domain.model.commands;

import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UpdateProfileCommandTest {

    private static final String VALID_FIRST_NAME = "Juan";
    private static final String VALID_LAST_NAME = "Pérez";
    private static final String VALID_EMAIL = "juan.perez@example.com";
    private static final String VALID_IMAGE = "profile.jpg";
    private static final String VALID_DNI = "12345678";
    private static final String VALID_ADDRESS = "Av. Principal 123";
    private static final String VALID_PHONE = "987654321";

    @Test
    @DisplayName("Debería crear comando de actualización con datos válidos")
    void testValidUpdateProfileCommand() {
        // Arrange & Act
        UpdateProfileCommand command = new UpdateProfileCommand(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_EMAIL,
                VALID_IMAGE,
                VALID_DNI,
                VALID_ADDRESS,
                VALID_PHONE
        );
        
        // Assert
        assertEquals(VALID_FIRST_NAME, command.firstName());
        assertEquals(VALID_LAST_NAME, command.lastName());
        assertEquals(VALID_EMAIL, command.email());
        assertEquals(VALID_IMAGE, command.image());
        assertEquals(VALID_DNI, command.dni());
        assertEquals(VALID_ADDRESS, command.address());
        assertEquals(VALID_PHONE, command.phone());
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Debería lanzar excepción cuando firstName es inválido durante actualización")
    void testInvalidFirstName(String invalidFirstName) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new UpdateProfileCommand(
                invalidFirstName,
                VALID_LAST_NAME,
                VALID_EMAIL,
                VALID_IMAGE,
                VALID_DNI,
                VALID_ADDRESS,
                VALID_PHONE
            )
        );
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Debería lanzar excepción cuando lastName es inválido durante actualización")
    void testInvalidLastName(String invalidLastName) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new UpdateProfileCommand(
                VALID_FIRST_NAME,
                invalidLastName,
                VALID_EMAIL,
                VALID_IMAGE,
                VALID_DNI,
                VALID_ADDRESS,
                VALID_PHONE
            )
        );
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Debería lanzar excepción cuando email es inválido durante actualización")
    void testInvalidEmail(String invalidEmail) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new UpdateProfileCommand(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                invalidEmail,
                VALID_IMAGE,
                VALID_DNI,
                VALID_ADDRESS,
                VALID_PHONE
            )
        );
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Debería lanzar excepción cuando image es inválido durante actualización")
    void testInvalidImage(String invalidImage) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new UpdateProfileCommand(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_EMAIL,
                invalidImage,
                VALID_DNI,
                VALID_ADDRESS,
                VALID_PHONE
            )
        );
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Debería lanzar excepción cuando dni es inválido durante actualización")
    void testInvalidDni(String invalidDni) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new UpdateProfileCommand(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_EMAIL,
                VALID_IMAGE,
                invalidDni,
                VALID_ADDRESS,
                VALID_PHONE
            )
        );
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Debería lanzar excepción cuando address es inválido durante actualización")
    void testInvalidAddress(String invalidAddress) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new UpdateProfileCommand(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_EMAIL,
                VALID_IMAGE,
                VALID_DNI,
                invalidAddress,
                VALID_PHONE
            )
        );
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Debería lanzar excepción cuando phone es inválido durante actualización")
    void testInvalidPhone(String invalidPhone) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new UpdateProfileCommand(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_EMAIL,
                VALID_IMAGE,
                VALID_DNI,
                VALID_ADDRESS,
                invalidPhone
            )
        );
    }
} 