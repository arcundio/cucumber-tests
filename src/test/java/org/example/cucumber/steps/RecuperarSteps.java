package org.example.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.cucumber.validation.JsonValidator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RecuperarSteps {


    HttpRequest postRequest;
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpResponse<String> httpResponse;
    String email;

    @Given("un correo valido")
    public void correoValido() {

        email = "santiagoarciniegasc@gmail.com";

    }

    @When("se envia una solicitud de recuperacion de contrasena")
    public void seEnviaUnaSolicitudDeRecuperacionDeContrasena() throws URISyntaxException, IOException, InterruptedException {

        postRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI("http://localhost:18082/usuarios/recuperarContraseña/"+email))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        httpResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

    }

    @Then("Se recibe mensaje de confirmacion de recuperacion")
    public void seRecibeMensajeDeConfirmacionDeRecuperacion() throws IOException {

        JsonValidator.validarJson(httpResponse.body());
        System.out.println(httpResponse.body());

    }
}
