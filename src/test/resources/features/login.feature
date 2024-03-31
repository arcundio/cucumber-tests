#language: en
#@allure.label.layer:rest
#@allure.label.owner:eroshenkoam
#@allure.label.url:/repos/{owner}/{repo}/labels
Feature: Inicio de sesión de usuarios

  Scenario: Iniciar sesión exitosamente
    Given un usuario válido con correo electrónico "sofia@mail.com" y contraseña "sofia789"
    When se envía una solicitud de inicio de sesión
    Then se recibe un token JWT válido


  Scenario: Iniciar sesión sin la contraseña o correo
    Given al json le falta el correo o contraseña
    When se envía una solicitud de inicio de sesión
    Then se recibe un mensaje informando de la solicitud inválida
