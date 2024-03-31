Feature: Eliminar un usuario
  Scenario: Eliminar un usuario dado un id
    Given un id de usuario valido
    And un Bearer jwt token valido
    When se envia una solicitud de eliminar usuario
    Then se recibe un mensaje de confirmacion de eliminacion

  Scenario: Eliminar un usuario dando un id invalido
    Given un id de usuario invalido
    And un Bearer jwt token valido
    When se envia una solicitud de eliminar usuario
    Then se recibe un mensaje informando error de servidor

  Scenario: Eliminar un usuario dando un token invalido
    Given un id de usuario valido
    And un Bearer jwt token invalido
    When se envia una solicitud de eliminar usuario
    Then se recibe un mensaje informando error de servidor

  Scenario: Eliminar un usuario sin la cabecera Authorization
    Given un id de usuario valido
    When se envia una solicitud de eliminar usuario sin el header Authorization
    Then se recibe un mensaje informando error de servidor


