Feature: se recupera la contraseña del usuario

  Scenario: se envía un token de recuperación por correo
    Given un correo valido
    When se envia una solicitud de recuperacion de contrasena
    Then Se recibe mensaje de confirmacion de recuperacion