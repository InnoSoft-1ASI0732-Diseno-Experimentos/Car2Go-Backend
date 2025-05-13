package com.pe.platform.profile.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.profiles.application.internal.commandservices.ProfileCommandServiceImpl;
import com.pe.platform.profiles.application.internal.queryservices.ProfileQueryServiceImpl;
import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.pe.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.pe.platform.profiles.domain.model.valueobjects.PaymentMethod;
import com.pe.platform.profiles.domain.model.valueobjects.PersonName;
import com.pe.platform.profiles.interfaces.rest.ProfilesController;
import com.pe.platform.profiles.interfaces.rest.resources.CreateProfileResource;
import com.pe.platform.profiles.interfaces.rest.resources.ProfileResource;
import com.pe.platform.profiles.interfaces.rest.resources.UpdateProfileResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfilesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfileCommandServiceImpl profileCommandService;

    @Mock
    private ProfileQueryServiceImpl profileQueryService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ProfilesController profilesController;

    private ObjectMapper objectMapper;

    private static final Long USER_ID = 1L;
    private static final String FIRST_NAME = "Juan";
    private static final String LAST_NAME = "Pérez";
    private static final String EMAIL = "juan.perez@example.com";
    private static final String IMAGE = "profile.jpg";
    private static final String DNI = "12345678";
    private static final String ADDRESS = "Av. Principal 123";
    private static final String PHONE = "987654321";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profilesController).build();
        objectMapper = new ObjectMapper();
        
        // Configurar contexto de seguridad
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Crear detalles de usuario con rol de vendedor
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        UserDetailsImpl userDetails = new UserDetailsImpl(USER_ID, "testuser", "test@example.com", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    // Método auxiliar para crear un perfil de prueba
    private Profile createSampleProfile() {
        Profile profile = new Profile(
            new CreateProfileCommand(
                FIRST_NAME,
                LAST_NAME,
                EMAIL,
                IMAGE,
                DNI,
                ADDRESS,
                PHONE
            ),
            USER_ID
        );
        
        // Establecer id mediante reflexión ya que normalmente se genera por la base de datos
        try {
            java.lang.reflect.Field idField = Profile.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(profile, 1);
        } catch (Exception e) {
            throw new RuntimeException("Error setting profile ID: " + e.getMessage());
        }
        
        return profile;
    }
    
    // Método auxiliar para crear un recurso de creación de perfil
    private CreateProfileResource createSampleCreateResource() {
        return new CreateProfileResource(
            FIRST_NAME,
            LAST_NAME,
            EMAIL,
            IMAGE,
            DNI,
            ADDRESS,
            PHONE
        );
    }
    
    // Método auxiliar para crear un recurso de actualización de perfil
    private UpdateProfileResource createSampleUpdateResource() {
        return new UpdateProfileResource(
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
    @DisplayName("Debería crear un perfil con datos válidos")
    void testCreateProfile() throws Exception {
        // Arrange
        CreateProfileResource resource = createSampleCreateResource();
        Profile profile = createSampleProfile();
        
        when(profileCommandService.handle(any(CreateProfileCommand.class))).thenReturn(Optional.of(profile));

        // Act & Assert
        mockMvc.perform(post("/api/v1/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.email").value(EMAIL));

        verify(profileCommandService, times(1)).handle(any(CreateProfileCommand.class));
    }

    @Test
    @DisplayName("Debería retornar 400 cuando falla la creación del perfil")
    void testCreateProfileFails() throws Exception {
        // Arrange
        CreateProfileResource resource = createSampleCreateResource();
        
        when(profileCommandService.handle(any(CreateProfileCommand.class))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(post("/api/v1/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isBadRequest());

        verify(profileCommandService, times(1)).handle(any(CreateProfileCommand.class));
    }

    @Test
    @DisplayName("Debería obtener el perfil del usuario autenticado")
    void testGetMyProfile() throws Exception {
        // Arrange
        Profile profile = createSampleProfile();
        
        when(profileQueryService.handle(any(GetProfileByIdQuery.class))).thenReturn(Optional.of(profile));

        // Act & Assert
        mockMvc.perform(get("/api/v1/profiles/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.email").value(EMAIL));

        verify(profileQueryService, times(1)).handle(any(GetProfileByIdQuery.class));
    }

    @Test
    @DisplayName("Debería retornar 404 cuando no se encuentra el perfil del usuario")
    void testGetMyProfileNotFound() throws Exception {
        // Arrange
        when(profileQueryService.handle(any(GetProfileByIdQuery.class))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/profiles/me"))
                .andExpect(status().isNotFound());

        verify(profileQueryService, times(1)).handle(any(GetProfileByIdQuery.class));
    }

    @Test
    @DisplayName("Debería actualizar el perfil del usuario autenticado")
    void testUpdateProfile() throws Exception {
        // Arrange
        UpdateProfileResource resource = createSampleUpdateResource();
        Profile updatedProfile = new Profile(
            new UpdateProfileCommand(
                "Carlos",
                "Rodríguez",
                "carlos.rodriguez@example.com",
                "new-profile.jpg",
                "87654321",
                "Calle Nueva 456",
                "123456789"
            )
        );
        
        // Establecer campos que normalmente se establecen por JPA
        try {
            java.lang.reflect.Field idField = Profile.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(updatedProfile, 1);
            
            java.lang.reflect.Field profileIdField = Profile.class.getDeclaredField("profileId");
            profileIdField.setAccessible(true);
            profileIdField.set(updatedProfile, USER_ID);
        } catch (Exception e) {
            throw new RuntimeException("Error setting profile fields: " + e.getMessage());
        }
        
        when(profileCommandService.handle(any(UpdateProfileCommand.class))).thenReturn(Optional.of(updatedProfile));

        // Act & Assert
        mockMvc.perform(put("/api/v1/profiles/me/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Carlos"))
                .andExpect(jsonPath("$.lastName").value("Rodríguez"))
                .andExpect(jsonPath("$.email").value("carlos.rodriguez@example.com"));

        verify(profileCommandService, times(1)).handle(any(UpdateProfileCommand.class));
    }

    @Test
    @DisplayName("Debería retornar 404 cuando falla la actualización del perfil")
    void testUpdateProfileNotFound() throws Exception {
        // Arrange
        UpdateProfileResource resource = createSampleUpdateResource();
        
        when(profileCommandService.handle(any(UpdateProfileCommand.class))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/v1/profiles/me/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isNotFound());

        verify(profileCommandService, times(1)).handle(any(UpdateProfileCommand.class));
    }

    @Test
    @DisplayName("Debería agregar un método de pago al perfil")
    void testAddPaymentMethod() throws Exception {
        // Arrange
        Profile profile = createSampleProfile();
        PaymentMethod paymentMethod = new PaymentMethod("Visa", "4111111111111111");
        
        when(profileQueryService.handle(any(GetProfileByIdQuery.class))).thenReturn(Optional.of(profile));
        when(profileCommandService.save(any(Profile.class))).thenReturn(Optional.of(profile));

        // Act & Assert
        mockMvc.perform(put("/api/v1/profiles/me/payment-methods/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentMethod)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME));

        verify(profileQueryService, times(1)).handle(any(GetProfileByIdQuery.class));
        verify(profileCommandService, times(1)).save(any(Profile.class));
    }

    @Test
    @DisplayName("Debería retornar 404 cuando no se encuentra perfil al agregar método de pago")
    void testAddPaymentMethodProfileNotFound() throws Exception {
        // Arrange
        PaymentMethod paymentMethod = new PaymentMethod("Visa", "4111111111111111");
        
        when(profileQueryService.handle(any(GetProfileByIdQuery.class))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/v1/profiles/me/payment-methods/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentMethod)))
                .andExpect(status().isNotFound());

        verify(profileQueryService, times(1)).handle(any(GetProfileByIdQuery.class));
        verify(profileCommandService, never()).save(any(Profile.class));
    }

    @Test
    @DisplayName("Debería rechazar agregar más de 3 métodos de pago")
    void testAddPaymentMethodExceedsLimit() throws Exception {
        // Arrange
        Profile profile = createSampleProfile();
        profile.addPaymentMethod(new PaymentMethod("Visa", "4111111111111111"));
        profile.addPaymentMethod(new PaymentMethod("Mastercard", "5555555555554444"));
        profile.addPaymentMethod(new PaymentMethod("BBVA", "123456789"));
        
        PaymentMethod newPaymentMethod = new PaymentMethod("PayPal", "user@example.com");
        
        when(profileQueryService.handle(any(GetProfileByIdQuery.class))).thenReturn(Optional.of(profile));

        // Act & Assert
        mockMvc.perform(put("/api/v1/profiles/me/payment-methods/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPaymentMethod)))
                .andExpect(status().isBadRequest());

        verify(profileQueryService, times(1)).handle(any(GetProfileByIdQuery.class));
        verify(profileCommandService, never()).save(any(Profile.class));
    }

    @Test
    @DisplayName("Debería actualizar un método de pago existente")
    void testUpdatePaymentMethod() throws Exception {
        // Arrange
        Profile profile = createSampleProfile();
        PaymentMethod existingPaymentMethod = new PaymentMethod("Visa", "4111111111111111");
        profile.addPaymentMethod(existingPaymentMethod);
        
        // Establecer ID del método de pago mediante reflexión
        try {
            java.lang.reflect.Field idField = PaymentMethod.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existingPaymentMethod, 1L);
        } catch (Exception e) {
            throw new RuntimeException("Error setting payment method ID: " + e.getMessage());
        }
        
        PaymentMethod updatedPaymentMethod = new PaymentMethod("Mastercard", "5555555555554444");
        
        when(profileQueryService.handle(any(GetProfileByIdQuery.class))).thenReturn(Optional.of(profile));
        when(profileCommandService.save(any(Profile.class))).thenReturn(Optional.of(profile));

        // Act & Assert
        mockMvc.perform(put("/api/v1/profiles/me/payment-methods/edit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPaymentMethod)))
                .andExpect(status().isOk());

        verify(profileQueryService, times(1)).handle(any(GetProfileByIdQuery.class));
        verify(profileCommandService, times(1)).save(any(Profile.class));
    }

    @Test
    @DisplayName("Debería eliminar un método de pago existente")
    void testDeletePaymentMethod() throws Exception {
        // Arrange
        Profile profile = createSampleProfile();
        PaymentMethod paymentMethod = new PaymentMethod("Visa", "4111111111111111");
        profile.addPaymentMethod(paymentMethod);
        
        // Establecer ID del método de pago mediante reflexión
        try {
            java.lang.reflect.Field idField = PaymentMethod.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(paymentMethod, 1L);
        } catch (Exception e) {
            throw new RuntimeException("Error setting payment method ID: " + e.getMessage());
        }
        
        when(profileQueryService.handle(any(GetProfileByIdQuery.class))).thenReturn(Optional.of(profile));
        when(profileCommandService.save(any(Profile.class))).thenReturn(Optional.of(profile));

        // Act & Assert
        mockMvc.perform(delete("/api/v1/profiles/me/payment-methods/delete/1"))
                .andExpect(status().isOk());

        verify(profileQueryService, times(1)).handle(any(GetProfileByIdQuery.class));
        verify(profileCommandService, times(1)).save(any(Profile.class));
    }

    @Test
    @DisplayName("Debería obtener un perfil por ID")
    void testGetProfileById() throws Exception {
        // Arrange
        Profile profile = createSampleProfile();
        
        when(profileQueryService.handle(any(GetProfileByIdQuery.class))).thenReturn(Optional.of(profile));

        // Act & Assert
        mockMvc.perform(get("/api/v1/profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.email").value(EMAIL));

        verify(profileQueryService, times(1)).handle(any(GetProfileByIdQuery.class));
    }

    @Test
    @DisplayName("Debería retornar 404 cuando no se encuentra el perfil por ID")
    void testGetProfileByIdNotFound() throws Exception {
        // Arrange
        when(profileQueryService.handle(any(GetProfileByIdQuery.class))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/profiles/999"))
                .andExpect(status().isNotFound());

        verify(profileQueryService, times(1)).handle(any(GetProfileByIdQuery.class));
    }
} 