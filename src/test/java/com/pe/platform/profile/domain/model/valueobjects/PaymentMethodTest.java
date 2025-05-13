package com.pe.platform.profile.domain.model.valueobjects;

import com.pe.platform.profiles.domain.model.valueobjects.PaymentMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodTest {

    private static final String VALID_TYPE = "BBVA";
    private static final String VALID_DETAILS = "12345678912345678912";
    
    @Test
    @DisplayName("Debería crear un método de pago con valores válidos")
    void testCreatePaymentMethod() {
        // Arrange & Act
        PaymentMethod paymentMethod = new PaymentMethod(VALID_TYPE, VALID_DETAILS);
        
        // Assert
        assertNull(paymentMethod.getId()); // ID es generado por la base de datos
        assertEquals(VALID_TYPE, paymentMethod.getType());
        assertEquals(VALID_DETAILS, paymentMethod.getDetails());
    }
    
    @Test
    @DisplayName("Debería actualizar el tipo de método de pago")
    void testUpdateType() {
        // Arrange
        PaymentMethod paymentMethod = new PaymentMethod(VALID_TYPE, VALID_DETAILS);
        String newType = "Visa";
        
        // Act
        paymentMethod.setType(newType);
        
        // Assert
        assertEquals(newType, paymentMethod.getType());
    }
    
    @Test
    @DisplayName("Debería actualizar los detalles del método de pago")
    void testUpdateDetails() {
        // Arrange
        PaymentMethod paymentMethod = new PaymentMethod(VALID_TYPE, VALID_DETAILS);
        String newDetails = "987654321098765432";
        
        // Act
        paymentMethod.setDetails(newDetails);
        
        // Assert
        assertEquals(newDetails, paymentMethod.getDetails());
    }
    
    @Test
    @DisplayName("Constructor predeterminado debería crear un objeto sin propiedades inicializadas")
    void testDefaultConstructor() {
        // Arrange & Act
        PaymentMethod paymentMethod = new PaymentMethod();
        
        // Assert
        assertNull(paymentMethod.getId());
        assertNull(paymentMethod.getType());
        assertNull(paymentMethod.getDetails());
    }
} 