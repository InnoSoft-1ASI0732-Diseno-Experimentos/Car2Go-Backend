package com.pe.platform.profiles.domain.model.aggregates;

import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.pe.platform.profiles.domain.model.valueobjects.PaymentMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    private final Long PROFILE_ID = 1L;

    @Test
    @DisplayName("Debería crear un perfil correctamente con CreateProfileCommand")
    void testCreateProfileWithCommand() {
        // Arrange
        CreateProfileCommand command = createSampleCommand();
        
        // Act
        Profile profile = new Profile(command, PROFILE_ID);
        
        // Assert
        assertEquals(command.firstName(), profile.getFirstName());
        assertEquals(command.lastName(), profile.getLastName());
        assertEquals(command.email(), profile.getEmail());
        assertEquals(command.image(), profile.getImage());
        assertEquals(command.dni(), profile.getDni());
        assertEquals(command.address(), profile.getAddress());
        assertEquals(command.phone(), profile.getPhone());
        assertEquals(PROFILE_ID, profile.getProfileId());
        assertTrue(profile.getPaymentMethods().isEmpty());
    }
    
    @Test
    @DisplayName("Debería crear un perfil correctamente con UpdateProfileCommand")
    void testCreateProfileWithUpdateCommand() {
        // Arrange
        UpdateProfileCommand command = createSampleUpdateCommand();
        
        // Act
        Profile profile = new Profile(command);
        
        // Assert
        assertEquals(command.firstName(), profile.getFirstName());
        assertEquals(command.lastName(), profile.getLastName());
        assertEquals(command.email(), profile.getEmail());
        assertEquals(command.image(), profile.getImage());
        assertEquals(command.dni(), profile.getDni());
        assertEquals(command.address(), profile.getAddress());
        assertEquals(command.phone(), profile.getPhone());
        assertTrue(profile.getPaymentMethods().isEmpty());
    }
    
    @Test
    @DisplayName("Debería actualizar el nombre correctamente")
    void testUpdateName() {
        // Arrange
        Profile profile = new Profile(createSampleCommand(), PROFILE_ID);
        String newFirstName = "Jane";
        String newLastName = "Smith";
        
        // Act
        profile.updateName(newFirstName, newLastName);
        
        // Assert
        assertEquals(newFirstName, profile.getFirstName());
        assertEquals(newLastName, profile.getLastName());
        assertEquals(PROFILE_ID, profile.getProfileId());
    }
    
    @Test
    @DisplayName("Debería establecer y obtener profileId correctamente")
    void testSetAndGetProfileId() {
        // Arrange
        Profile profile = new Profile(createSampleCommand(), PROFILE_ID);
        Long newProfileId = 2L;
        
        // Act
        profile.setProfileId(newProfileId);
        
        // Assert
        assertEquals(newProfileId, profile.getProfileId());
    }
    
    @Test
    @DisplayName("Debería agregar método de pago correctamente")
    void testAddPaymentMethod() {
        // Arrange
        Profile profile = new Profile(createSampleCommand(), PROFILE_ID);
        PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");
        
        // Act
        profile.addPaymentMethod(paymentMethod);
        
        // Assert
        assertEquals(1, profile.getPaymentMethods().size());
        assertEquals(paymentMethod, profile.getPaymentMethods().get(0));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al agregar más de 3 métodos de pago")
    void testAddPaymentMethodExceedingLimit() {
        // Arrange
        Profile profile = new Profile(createSampleCommand(), PROFILE_ID);
        profile.addPaymentMethod(new PaymentMethod("VISA", "1111-2222-3333-4444"));
        profile.addPaymentMethod(new PaymentMethod("MASTERCARD", "5555-6666-7777-8888"));
        profile.addPaymentMethod(new PaymentMethod("AMEX", "9999-0000-1111-2222"));
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            profile.addPaymentMethod(new PaymentMethod("PAYPAL", "user@example.com")));
        assertEquals(3, profile.getPaymentMethods().size());
    }
    
    @Test
    @DisplayName("Debería eliminar método de pago por ID correctamente")
    void testRemovePaymentMethodById() {
        // Arrange
        Profile profile = new Profile(createSampleCommand(), PROFILE_ID);
        PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");
        profile.addPaymentMethod(paymentMethod);
        
        // Establecer ID manualmente ya que no tenemos persistencia real en el test
        setPaymentMethodId(paymentMethod, 1L);
        
        // Act
        boolean removed = profile.removePaymentMethodById(1L);
        
        // Assert
        assertTrue(removed);
        assertTrue(profile.getPaymentMethods().isEmpty());
    }
    
    @Test
    @DisplayName("Debería retornar false al intentar eliminar un método de pago inexistente")
    void testRemoveNonExistentPaymentMethod() {
        // Arrange
        Profile profile = new Profile(createSampleCommand(), PROFILE_ID);
        PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");
        profile.addPaymentMethod(paymentMethod);
        
        // Establecer ID manualmente
        setPaymentMethodId(paymentMethod, 1L);
        
        // Act
        boolean removed = profile.removePaymentMethodById(999L);
        
        // Assert
        assertFalse(removed);
        assertEquals(1, profile.getPaymentMethods().size());
    }
    
    @Test
    @DisplayName("Debería actualizar método de pago correctamente")
    void testUpdatePaymentMethod() {
        // Arrange
        Profile profile = new Profile(createSampleCommand(), PROFILE_ID);
        PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");
        profile.addPaymentMethod(paymentMethod);
        
        // Establecer ID manualmente
        setPaymentMethodId(paymentMethod, 1L);
        
        PaymentMethod updatedPaymentMethod = new PaymentMethod("MASTERCARD", "9876-5432-1098-7654");
        
        // Act
        boolean updated = profile.updatePaymentMethod(1L, updatedPaymentMethod);
        
        // Assert
        assertTrue(updated);
        assertEquals(1, profile.getPaymentMethods().size());
        assertEquals("MASTERCARD", profile.getPaymentMethods().get(0).getType());
        assertEquals("9876-5432-1098-7654", profile.getPaymentMethods().get(0).getDetails());
    }
    
    @Test
    @DisplayName("Debería retornar false al intentar actualizar un método de pago inexistente")
    void testUpdateNonExistentPaymentMethod() {
        // Arrange
        Profile profile = new Profile(createSampleCommand(), PROFILE_ID);
        PaymentMethod paymentMethod = new PaymentMethod("VISA", "1234-5678-9012-3456");
        profile.addPaymentMethod(paymentMethod);
        
        // Establecer ID manualmente
        setPaymentMethodId(paymentMethod, 1L);
        
        PaymentMethod updatedPaymentMethod = new PaymentMethod("MASTERCARD", "9876-5432-1098-7654");
        
        // Act
        boolean updated = profile.updatePaymentMethod(999L, updatedPaymentMethod);
        
        // Assert
        assertFalse(updated);
        assertEquals("VISA", profile.getPaymentMethods().get(0).getType());
        assertEquals("1234-5678-9012-3456", profile.getPaymentMethods().get(0).getDetails());
    }
    
    /**
     * Establece el ID de un PaymentMethod usando reflexión
     */
    private void setPaymentMethodId(PaymentMethod paymentMethod, Long id) {
        try {
            java.lang.reflect.Field idField = PaymentMethod.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(paymentMethod, id);
        } catch (Exception e) {
            throw new RuntimeException("Error setting payment method ID: " + e.getMessage());
        }
    }
    
    /**
     * Crea un comando de muestra para las pruebas
     */
    private CreateProfileCommand createSampleCommand() {
        return new CreateProfileCommand(
                "John",
                "Doe",
                "john.doe@example.com",
                "profile-image.jpg",
                "12345678",
                "Calle Principal 123",
                "+51987654321"
        );
    }
    
    /**
     * Crea un comando de actualización de muestra para las pruebas
     */
    private UpdateProfileCommand createSampleUpdateCommand() {
        return new UpdateProfileCommand(
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "new-profile-image.jpg",
                "87654321",
                "Avenida Secundaria 456",
                "+51123456789"
        );
    }
} 