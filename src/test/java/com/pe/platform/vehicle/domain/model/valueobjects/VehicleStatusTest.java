package com.pe.platform.vehicle.domain.model.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class vehicleStatusTest {

    @Test
    @DisplayName("Debería contener los valores correctos")
    void testEnumValues() {
        // Arrange & Act: Obtener los valores del enum
        vehicleStatus[] values = vehicleStatus.values();
        
        // Assert: Verificar que existen todos los valores esperados
        assertEquals(3, values.length);
        assertEquals(vehicleStatus.PENDING, values[0]);
        assertEquals(vehicleStatus.REVIEWED, values[1]);
        assertEquals(vehicleStatus.REJECT, values[2]);
    }
    
    @Test
    @DisplayName("Debería convertir correctamente de String a enum")
    void testFromString() {
        // Act & Assert: Verificar conversión de String a enum
        assertEquals(vehicleStatus.PENDING, vehicleStatus.valueOf("PENDING"));
        assertEquals(vehicleStatus.REVIEWED, vehicleStatus.valueOf("REVIEWED"));
        assertEquals(vehicleStatus.REJECT, vehicleStatus.valueOf("REJECT"));
    }
    
    @Test
    @DisplayName("Debería lanzar IllegalArgumentException para un valor inválido")
    void testInvalidValue() {
        // Act & Assert: Verificar que se lanza excepción para valor inválido
        assertThrows(IllegalArgumentException.class, () -> vehicleStatus.valueOf("INVALID_STATUS"));
    }
} 