Feature: actualizar la contraseña del usuario

  Scenario: Se actualiza exitosamente la clave del usuario
    Given un id valido y un json valido del usuario
    And un jwt token valido
    When se envia una solicitud de clave de usuario
    Then se recibe mensaje de confirmacion de actualizacion

  Scenario: se actualiza la contraseña con un token invalido
    Given un id valido y un json valido del usuario
    And un jwt token invalido
    When se envia una solicitud de clave de usuario
    Then se recibe mensaje informando error

  Scenario: se actualiza la contraseña sin el header Authorization
    Given un id valido y un json valido del usuario
    When se envia una solicitud de clave de usuario sin el header Authorization
    Then se recibe mensaje informando error