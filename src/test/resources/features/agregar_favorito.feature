Feature: Guardar vehículos como favoritos

  Scenario: Un comprador agrega un vehículo a favoritos exitosamente
    Given el comprador ha iniciado sesión con rol BUYER
    And el comprador tiene el ID del vehículo disponible
    When envía una solicitud POST a "/api/v1/favorites/3"
    Then la respuesta del API es 200 (OK)
