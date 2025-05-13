package com.pe.platform.profiles.domain.model.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonNameTest {

    @Test
    @DisplayName("Debería crear PersonName correctamente")
    void testCreatePersonName() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        
        // Act
        PersonName personName = new PersonName(firstName, lastName);
        
        // Assert
        assertEquals(firstName, personName.getFirstName());
        assertEquals(lastName, personName.getLastName());
        assertEquals("John Doe", personName.getFullName());
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando firstName es nulo")
    void testCreatePersonNameWithNullFirstName() {
        // Arrange
        String firstName = null;
        String lastName = "Doe";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PersonName(firstName, lastName));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando firstName está vacío")
    void testCreatePersonNameWithEmptyFirstName() {
        // Arrange
        String firstName = "";
        String lastName = "Doe";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PersonName(firstName, lastName));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando firstName está en blanco")
    void testCreatePersonNameWithBlankFirstName() {
        // Arrange
        String firstName = "   ";
        String lastName = "Doe";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PersonName(firstName, lastName));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando lastName es nulo")
    void testCreatePersonNameWithNullLastName() {
        // Arrange
        String firstName = "John";
        String lastName = null;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PersonName(firstName, lastName));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando lastName está vacío")
    void testCreatePersonNameWithEmptyLastName() {
        // Arrange
        String firstName = "John";
        String lastName = "";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PersonName(firstName, lastName));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando lastName está en blanco")
    void testCreatePersonNameWithBlankLastName() {
        // Arrange
        String firstName = "John";
        String lastName = "   ";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PersonName(firstName, lastName));
    }
} 