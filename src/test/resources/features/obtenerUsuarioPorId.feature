Feature: Obtener usuario por id
  Scenario: Se obtiene el usuario dado un id
    Given el id de un usuario válido
    When se envía una solicitud de obtener usuario
    Then se recibe un json del usuario

  Scenario: Se obtiene un error dando un id equivocado
    Given un id que no corresponde a un usuario
    When se envía una solicitud de obtener usuario
    Then se recibe un error del servidor
