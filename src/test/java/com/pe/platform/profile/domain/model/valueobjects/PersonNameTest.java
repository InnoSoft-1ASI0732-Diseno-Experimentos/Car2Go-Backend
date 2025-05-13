package com.pe.platform.profile.domain.model.valueobjects;

import com.pe.platform.profiles.domain.model.valueobjects.PersonName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PersonNameTest {

    @Test
    @DisplayName("Debería crear un nombre válido")
    void testValidPersonName() {
        // Arrange & Act
        PersonName personName = new PersonName("Juan", "Pérez");
        
        // Assert
        assertEquals("Juan", personName.getFirstName());
        assertEquals("Pérez", personName.getLastName());
        assertEquals("Juan Pérez", personName.getFullName());
    }
    
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "\t", "\n"})
    @DisplayName("Debería lanzar excepción cuando firstName es nulo o vacío")
    void testInvalidFirstName(String invalidFirstName) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, 
                     () -> new PersonName(invalidFirstName, "Pérez"));
    }
    
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "\t", "\n"})
    @DisplayName("Debería lanzar excepción cuando lastName es nulo o vacío")
    void testInvalidLastName(String invalidLastName) {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, 
                     () -> new PersonName("Juan", invalidLastName));
    }
    
    @ParameterizedTest
    @CsvSource({
        "Ana, María, Ana María",
        "Carlos, González, Carlos González",
        "José Luis, Rodríguez, José Luis Rodríguez"
    })
    @DisplayName("Debería generar el nombre completo correctamente")
    void testFullName(String firstName, String lastName, String expectedFullName) {
        // Arrange & Act
        PersonName personName = new PersonName(firstName, lastName);
        
        // Assert
        assertEquals(expectedFullName, personName.getFullName());
    }
} 