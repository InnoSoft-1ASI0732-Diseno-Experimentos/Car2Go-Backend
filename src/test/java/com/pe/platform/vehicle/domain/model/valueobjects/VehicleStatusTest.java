package com.pe.platform.vehicle.domain.model.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleStatusTest {

    @Test
    @DisplayName("Should have correct status values defined")
    void testStatusValues() {
        // Arrange & Act & Assert
        vehicleStatus[] statuses = vehicleStatus.values();
        
        // Verify there are exactly 3 statuses
        assertEquals(3, statuses.length);
        
        // Verify the statuses are the expected ones
        assertArrayEquals(
            new vehicleStatus[] {
                vehicleStatus.PENDING,
                vehicleStatus.REVIEWED,
                vehicleStatus.REJECT
            },
            statuses
        );
    }

    @Test
    @DisplayName("Should convert string to enum correctly")
    void testValueOf() {
        // Act & Assert
        assertEquals(vehicleStatus.PENDING, vehicleStatus.valueOf("PENDING"));
        assertEquals(vehicleStatus.REVIEWED, vehicleStatus.valueOf("REVIEWED"));
        assertEquals(vehicleStatus.REJECT, vehicleStatus.valueOf("REJECT"));
    }

    @Test
    @DisplayName("Should throw exception when converting invalid string")
    void testInvalidValueOf() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> vehicleStatus.valueOf("INVALID_STATUS"));
    }

    @Test
    @DisplayName("Statuses should be different from each other")
    void testStatusEquality() {
        // Arrange
        vehicleStatus pending = vehicleStatus.PENDING;
        vehicleStatus reviewed = vehicleStatus.REVIEWED;
        vehicleStatus reject = vehicleStatus.REJECT;
        
        // Act & Assert
        assertNotEquals(pending, reviewed);
        assertNotEquals(pending, reject);
        assertNotEquals(reviewed, reject);
        
        assertEquals(pending, vehicleStatus.PENDING);
        assertEquals(reviewed, vehicleStatus.REVIEWED);
        assertEquals(reject, vehicleStatus.REJECT);
    }
} 