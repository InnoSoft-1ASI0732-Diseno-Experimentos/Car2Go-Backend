package com.pe.platform.profiles.domain.model.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The type Payment method test.
 */
class PaymentMethodTest {

  /**
   * Test create payment method.
   */
  @Test
  @DisplayName("Debería crear PaymentMethod correctamente")
  void testCreatePaymentMethod() {
    // Arrange
    String type = "VISA";
    String details = "1234-5678-9012-3456";

    // Act
    PaymentMethod paymentMethod = new PaymentMethod(type, details);

    // Assert
    assertEquals(type, paymentMethod.getType());
    assertEquals(details, paymentMethod.getDetails());
    assertNull(paymentMethod.getId()); // ID debería ser nulo hasta que se persista
  }

  /**
   * Test set type.
   */
  @Test
  @DisplayName("Debería modificar tipo correctamente")
  void testSetType() {
    // Arrange
    PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");
    String newType = "MASTERCARD";

    // Act
    paymentMethod.setType(newType);

    // Assert
    assertEquals(newType, paymentMethod.getType());
  }

  /**
   * Test set details.
   */
  @Test
  @DisplayName("Debería modificar detalles correctamente")
  void testSetDetails() {
    // Arrange
    PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");
    String newDetails = "9876-5432-1098-7654";

    // Act
    paymentMethod.setDetails(newDetails);

    // Assert
    assertEquals(newDetails, paymentMethod.getDetails());
  }

  /**
   * Test no args constructor.
   */
  @Test
  @DisplayName("Constructor sin argumentos debería crear objeto válido")
  void testNoArgsConstructor() {
    // Act
    PaymentMethod paymentMethod = new PaymentMethod();

    // Assert
    assertNotNull(paymentMethod);
    assertNull(paymentMethod.getType());
    assertNull(paymentMethod.getDetails());
    assertNull(paymentMethod.getId());
  }
} 