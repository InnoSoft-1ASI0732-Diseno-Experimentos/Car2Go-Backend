package com.pe.platform.bdd;

import io.cucumber.java.en.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class VehiclePublicationSteps {

    private ResponseEntity<String> response;
    private HttpHeaders headers = new HttpHeaders();
    private final String baseUrl = "http://localhost:8080/api/v1/vehicle";

    @Given("el vendedor tiene la sesión iniciada con rol SELLER")
    public void vendedorConSesionIniciada() {
        // Suponiendo uso de JWT autenticado
        String jwtToken = "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJSYXNlYyIsImlhdCI6MTc0NzIwODI3MCwiZXhwIjoxNzQ3ODEzMDcwfQ.nG4wl0PzNf7DJxwTeywsMmUnVHNtGRv9-WptLAI43N6YZnXnYau7B0qJuFJQCST8"; // token válido con ROLE_SELLER
        headers.set("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Given("el vendedor tiene acceso a la sección {string}")
    public void accesoASeccion(String seccion) {
        assertEquals("Publicar Auto", seccion);
    }

    @When("envía una solicitud POST a {string} con los siguientes datos del auto:")
    public void enviaSolicitudPostConDatos(String endpoint, Map<String, String> datos) {
        String json = """
        {
          "name": "%s", "phone": "%s", "email": "%s", "brand": "%s",
          "model": "%s", "color": "%s", "year": "%s", "price": %s,
          "transmission": "%s", "engine": "%s", "mileage": %s,
          "doors": "%s", "plate": "%s", "location": "%s",
          "description": "%s", "images": [%s], "fuel": "%s", "speed": %s
        }
        """.formatted(
                datos.get("name"), datos.get("phone"), datos.get("email"), datos.get("brand"),
                datos.get("model"), datos.get("color"), datos.get("year"), datos.get("price"),
                datos.get("transmission"), datos.get("engine"), datos.get("mileage"),
                datos.get("doors"), datos.get("plate"), datos.get("location"),
                datos.get("description"),
                formatImageList(datos.get("images")),
                datos.get("fuel"), datos.get("speed")
        );

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.postForEntity(baseUrl, request, String.class);
    }

    @Then("la API responde con estado {int} \\({word})")
    public void laApiRespondeConEstado(int statusCode, String statusName) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }

    @Then("el vehículo queda registrado con estado {word}")
    public void vehiculoRegistradoConEstado(String estadoEsperado) {
        assertTrue(response.getBody().contains("\"status\":\"" + estadoEsperado + "\""));
    }

    private String formatImageList(String imagesCsv) {
        return "\"" + String.join("\",\"", imagesCsv.split(",")) + "\"";
    }
}
