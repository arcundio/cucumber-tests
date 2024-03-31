package org.example.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.cucumber.helperclasses.BearerTokenGenerator;
import org.example.cucumber.validation.JsonValidator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class EliminarSteps {

    String idUsuario;
    String jwtToken;
    HttpRequest deleteRequest;
    HttpClient httpClient = HttpClient.newHttpClient();
    static HttpResponse<String> httpResponse;

    @Given("un id de usuario valido")
    public void idUsuarioValido() {
        idUsuario = String.valueOf(new Random().nextInt(31));
    }

    @And("un Bearer jwt token valido")
    public void unBearerJwtTokenValido() throws URISyntaxException, IOException, InterruptedException {
        jwtToken = BearerTokenGenerator.generateToken();
    }

    @When("se envia una solicitud de eliminar usuario")
    public void seEnviaUnaSolicitudDeEliminarUsuario() throws URISyntaxException, IOException, InterruptedException {

        deleteRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer "+jwtToken)
                .uri(new URI("http://localhost:18082/usuarios/"+idUsuario))
                .DELETE()
                .build();

        httpResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

    }

    @Then("se recibe un mensaje de confirmacion de eliminacion")
    public void seRecibeUnMensajeDeConfirmacionDeEliminacion() throws IOException {

        JsonValidator.validarJson(httpResponse.body());
        System.out.println(httpResponse.body());

    }

    @Given("un id de usuario invalido")
    public void unIdDeUsuarioInvalido() {

        idUsuario = "100000";
        
    }

    @Then("se recibe un mensaje informando error de servidor")
    public void seRecibeUnMensajeInformandoErrorDeServidor() throws IOException {

        JsonValidator.validarJson(httpResponse.body());
        System.out.println(httpResponse.body());
        
    }

    @And("un Bearer jwt token invalido")
    public void unBearerJwtTokenInvalido() throws URISyntaxException, IOException, InterruptedException {
        jwtToken = BearerTokenGenerator.generateToken() + "dd";
    }

    @When("se envia una solicitud de eliminar usuario sin el header Authorization")
    public void seEnviaUnaSolicitudDeEliminarUsuarioSinElHeaderAuthorization() throws URISyntaxException, IOException, InterruptedException {

        deleteRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:18082/usuarios/"+idUsuario))
                .DELETE()
                .build();
        httpResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

    }
}
