Feature: actualizar un usuario
  Scenario: se actualiza un usuario con la info correcta
    Given un json con un usuario valido
    And un Bearer token valido
    When se envia una solicitud de actualizar usuario
    Then se recibe un mensaje de confirmacion

  Scenario: se actualiza un usuario con la info correcta pero con el token invalido
    Given un json con un usuario valido
    And un Bearer token invalido
    When se envia una solicitud de actualizar usuario
    Then se recibe un mensaje informando que el token no es valido

  Scenario: se actualiza un usuario con la info correcta pero sin la cabecera Authorization
    Given un json con un usuario valido
    When se envia una solicitud de actualizar usuario sin el header Authorization
    Then se recibe un mensaje informando que se requiere el header Authorization
