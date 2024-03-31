#language: en
#@allure.label.layer:crear
#@allure.label.owner:eroshenkoam
#@allure.label.url:/repos/{owner}/{repo}/labels
Feature: crear usuario

  Scenario: Crear un usuario exitosamente
    Given un DTO con la info necesaria para crear el usuario
    When se envía una solicitud de crear usuario
    Then se recibe un mensaje de confirmación
