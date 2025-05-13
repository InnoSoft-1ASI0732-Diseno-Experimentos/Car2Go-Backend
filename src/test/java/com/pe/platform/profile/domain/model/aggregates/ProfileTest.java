package com.pe.platform.profile.domain.model.aggregates;

import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.pe.platform.profiles.domain.model.valueobjects.PaymentMethod;
import com.pe.platform.profiles.domain.model.valueobjects.PersonName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    private static final String VALID_FIRST_NAME = "Juan";
    private static final String VALID_LAST_NAME = "Pérez";
    private static final String VALID_EMAIL = "juan.perez@example.com";
    private static final String VALID_IMAGE = "profile.jpg";
    private static final String VALID_DNI = "12345678";
    private static final String VALID_ADDRESS = "Av. Principal 123";
    private static final String VALID_PHONE = "987654321";
    private static final Long VALID_PROFILE_ID = 1L;
    
    private CreateProfileCommand validCreateCommand;
    private UpdateProfileCommand validUpdateCommand;
    
    @BeforeEach
    void setUp() {
        validCreateCommand = new CreateProfileCommand(
            VALID_FIRST_NAME,
            VALID_LAST_NAME,
            VALID_EMAIL,
            VALID_IMAGE,
            VALID_DNI,
            VALID_ADDRESS,
            VALID_PHONE
        );
        
        validUpdateCommand = new UpdateProfileCommand(
            "Carlos",
            "Rodríguez", 
            "carlos.rodriguez@example.com",
            "new-profile.jpg",
            "87654321",
            "Calle Nueva 456",
            "123456789"
        );
    }
    
    @Test
    @DisplayName("Debería crear un perfil correctamente con CreateProfileCommand")
    void testProfileCreationWithCommand() {
        // Arrange & Act
        Profile profile = new Profile(validCreateCommand, VALID_PROFILE_ID);
        
        // Assert
        assertEquals(VALID_FIRST_NAME, profile.getFirstName());
        assertEquals(VALID_LAST_NAME, profile.getLastName());
        assertEquals(VALID_EMAIL, profile.getEmail());
        assertEquals(VALID_IMAGE, profile.getImage());
        assertEquals(VALID_DNI, profile.getDni());
        assertEquals(VALID_ADDRESS, profile.getAddress());
        assertEquals(VALID_PHONE, profile.getPhone());
        assertEquals(VALID_PROFILE_ID, profile.getProfileId());
        assertTrue(profile.getPaymentMethods().isEmpty());
    }
    
    @Test
    @DisplayName("Debería crear un perfil correctamente con UpdateProfileCommand")
    void testProfileCreationWithUpdateCommand() {
        // Arrange & Act
        Profile profile = new Profile(validUpdateCommand);
        
        // Assert
        assertEquals("Carlos", profile.getFirstName());
        assertEquals("Rodríguez", profile.getLastName());
        assertEquals("carlos.rodriguez@example.com", profile.getEmail());
        assertEquals("new-profile.jpg", profile.getImage());
        assertEquals("87654321", profile.getDni());
        assertEquals("Calle Nueva 456", profile.getAddress());
        assertEquals("123456789", profile.getPhone());
        assertTrue(profile.getPaymentMethods().isEmpty());
    }
    
    @Test
    @DisplayName("Debería establecer y recuperar correctamente el profileId")
    void testSetAndGetProfileId() {
        // Arrange
        Profile profile = new Profile(validCreateCommand, 0L);
        Long newProfileId = 12345L;
        
        // Act
        profile.setProfileId(newProfileId);
        
        // Assert
        assertEquals(newProfileId, profile.getProfileId());
    }
    
    @Test
    @DisplayName("Debería actualizar el nombre correctamente")
    void testUpdateName() {
        // Arrange
        Profile profile = new Profile(validCreateCommand, VALID_PROFILE_ID);
        String newFirstName = "Pedro";
        String newLastName = "González";
        
        // Act
        profile.updateName(newFirstName, newLastName);
        
        // Assert
        assertEquals(newFirstName, profile.getFirstName());
        assertEquals(newLastName, profile.getLastName());
        PersonName personName = profile.getName();
        assertEquals(newFirstName, personName.getFirstName());
        assertEquals(newLastName, personName.getLastName());
        assertEquals(newFirstName + " " + newLastName, personName.getFullName());
    }
    
    @Test
    @DisplayName("Debería agregar un método de pago correctamente")
    void testAddPaymentMethod() {
        // Arrange
        Profile profile = new Profile(validCreateCommand, VALID_PROFILE_ID);
        PaymentMethod paymentMethod = new PaymentMethod("Visa", "4111111111111111");
        
        // Act
        profile.addPaymentMethod(paymentMethod);
        
        // Assert
        assertEquals(1, profile.getPaymentMethods().size());
        assertEquals("Visa", profile.getPaymentMethods().get(0).getType());
        assertEquals("4111111111111111", profile.getPaymentMethods().get(0).getDetails());
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al exceder el máximo de métodos de pago")
    void testAddTooManyPaymentMethods() {
        // Arrange
        Profile profile = new Profile(validCreateCommand, VALID_PROFILE_ID);
        profile.addPaymentMethod(new PaymentMethod("Visa", "4111111111111111"));
        profile.addPaymentMethod(new PaymentMethod("Mastercard", "5555555555554444"));
        profile.addPaymentMethod(new PaymentMethod("BBVA", "123456789"));
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            profile.addPaymentMethod(new PaymentMethod("PayPal", "user@example.com")));
    }
    
    @Test
    @DisplayName("Debería eliminar un método de pago correctamente")
    void testRemovePaymentMethod() {
        // Arrange
        Profile profile = new Profile(validCreateCommand, VALID_PROFILE_ID);
        PaymentMethod paymentMethod = new PaymentMethod("Visa", "4111111111111111");
        profile.addPaymentMethod(paymentMethod);
        
        // Need to set the ID since it would normally be set by the persistence layer
        try {
            java.lang.reflect.Field idField = PaymentMethod.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(paymentMethod, 1L);
        } catch (Exception e) {
            fail("Failed to set ID field via reflection: " + e.getMessage());
        }
        
        // Act
        boolean removed = profile.removePaymentMethodById(1L);
        
        // Assert
        assertTrue(removed);
        assertTrue(profile.getPaymentMethods().isEmpty());
    }
    
    @Test
    @DisplayName("Debería actualizar un método de pago correctamente")
    void testUpdatePaymentMethod() {
        // Arrange
        Profile profile = new Profile(validCreateCommand, VALID_PROFILE_ID);
        PaymentMethod paymentMethod = new PaymentMethod("Visa", "4111111111111111");
        profile.addPaymentMethod(paymentMethod);
        
        // Need to set the ID since it would normally be set by the persistence layer
        try {
            java.lang.reflect.Field idField = PaymentMethod.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(paymentMethod, 1L);
        } catch (Exception e) {
            fail("Failed to set ID field via reflection: " + e.getMessage());
        }
        
        // Act
        PaymentMethod updatedMethod = new PaymentMethod("Mastercard", "5555555555554444");
        boolean updated = profile.updatePaymentMethod(1L, updatedMethod);
        
        // Assert
        assertTrue(updated);
        assertEquals(1, profile.getPaymentMethods().size());
        assertEquals("Mastercard", profile.getPaymentMethods().get(0).getType());
        assertEquals("5555555555554444", profile.getPaymentMethods().get(0).getDetails());
    }
    
    @Test
    @DisplayName("Debería retornar false al intentar actualizar un método de pago inexistente")
    void testUpdateNonExistentPaymentMethod() {
        // Arrange
        Profile profile = new Profile(validCreateCommand, VALID_PROFILE_ID);
        PaymentMethod updatedMethod = new PaymentMethod("Mastercard", "5555555555554444");
        
        // Act
        boolean updated = profile.updatePaymentMethod(999L, updatedMethod);
        
        // Assert
        assertFalse(updated);
    }
} 