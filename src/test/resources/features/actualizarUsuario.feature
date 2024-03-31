Feature: actualizar un usuario
  Scenario: se actualiza un usuario con la info correcta
    Given un json con un usuario valido
    And un Bearer token valido
    When se envia una solicitud de actualizar usuario
    Then se recibe un mensaje de confirmacion

