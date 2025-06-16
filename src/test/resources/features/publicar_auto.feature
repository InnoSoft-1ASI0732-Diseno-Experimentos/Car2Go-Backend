Feature: Publicación de vehículos

  Scenario: Un vendedor publica un nuevo vehículo exitosamente
    Given el vendedor tiene la sesión iniciada con rol SELLER
    And el vendedor tiene acceso a la sección "Publicar Auto"
    When envía una solicitud POST a "/api/v1/vehicle" con los siguientes datos del auto:
      | name         | Sedan Azul 2023     |
      | phone        | 987654321           |
      | email        | vendedor@mail.com   |
      | brand        | Toyota              |
      | model        | Corolla             |
      | color        | Azul                |
      | year         | 2023                |
      | price        | 18000               |
      | transmission | Automática          |
      | engine       | 1.8L                |
      | mileage      | 15000               |
      | doors        | 4                   |
      | plate        | ABC123              |
      | location     | Lima                |
      | description  | En excelente estado |
      | images       | auto1.jpg,auto2.jpg |
      | fuel         | Gasolina            |
      | speed        | 180                 |
    Then la API responde con estado 201 (CREATED)
    And el vehículo queda registrado con estado PENDING
