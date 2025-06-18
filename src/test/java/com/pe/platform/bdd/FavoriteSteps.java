package com.pe.platform.bdd;

import io.cucumber.java.en.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class FavoriteSteps {

    private ResponseEntity<String> response;
    private HttpHeaders headers = new HttpHeaders();
    private final String baseUrl = "http://localhost:8080/api/v1/favorites/";

    @Given("el comprador ha iniciado sesión con rol BUYER")
    public void compradorSesionIniciada() {
        System.out.println("✅ [GIVEN] Comprador autenticado con rol BUYER.");
        String jwtToken = "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJDZXNhciIsImlhdCI6MTc0NzUzNzIxMSwiZXhwIjoxNzQ4MTQyMDExfQ.GbWlakqrGGQe3HRJlhx_5uZ3hTJORhvMujX7zq6VT_b4mif1pZk-phcxSqHSai4l";
        headers.set("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Given("el comprador tiene el ID del vehículo disponible")
    public void compradorTieneVehiculo() {
        System.out.println("🔍 [GIVEN] El comprador tiene el ID del vehículo listo para agregar a favoritos.");
    }

    @When("envía una solicitud POST a {string}")
    public void enviaSolicitudPostAFavorito(String endpoint) {
        System.out.println("📤 [WHEN] Enviando solicitud POST a " + endpoint);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.exchange("http://localhost:8080" + endpoint, HttpMethod.POST, request, String.class);
        System.out.println("✅ [WHEN] Solicitud enviada. Código de respuesta: " + response.getStatusCodeValue());
    }

    @Then("la respuesta del API es {int} \\({word})")
    public void verificarRespuestaFavoritos(int code, String estado) {
        System.out.println("📥 [THEN] Verificando código de respuesta esperado: " + code + " (" + estado + ")");
        assertEquals(code, response.getStatusCodeValue());
        System.out.println("✅ [THEN] La respuesta fue correcta: " + code + " (" + estado + ")");
    }
}
