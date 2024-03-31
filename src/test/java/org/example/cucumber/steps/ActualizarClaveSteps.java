package org.example.cucumber.steps;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.cucumber.helperclasses.BearerTokenGenerator;
import org.example.cucumber.model.UsuarioModel;
import org.example.cucumber.validation.JsonValidator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ActualizarClaveSteps {

    UsuarioModel usuario = new UsuarioModel();
    String idUsuario;
    String jwtToken;
    HttpRequest putRequest;
    HttpClient httpClient = HttpClient.newHttpClient();
    static HttpResponse<String> httpResponse;


    @Given("un id valido y un json valido del usuario")
    public void idYJsonValido() {

        idUsuario = "10";
        Faker faker = new Faker();
        String fakerPassword = faker.internet().password();
        fakerPassword = fakerPassword.substring(0, Math.min(fakerPassword.length(), 12));

        usuario.setContrasena(fakerPassword);


    }

    @And("un jwt token valido")
    public void unJwtTokenValido() throws URISyntaxException, IOException, InterruptedException {

        jwtToken = BearerTokenGenerator.generateToken();

    }

    @When("se envia una solicitud de clave de usuario")
    public void seEnviaUnaSolicitudDeClaveDeUsuario() throws URISyntaxException, IOException, InterruptedException {

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(usuario);

        putRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+jwtToken)
                .uri(new URI("http://localhost:18082/usuarios/actualizarContraseña/"+idUsuario))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        httpResponse = httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());


    }

    @Then("se recibe mensaje de confirmacion de actualizacion")
    public void seRecibeMensajeDeConfirmacionDeActualizacion() throws IOException {

        JsonValidator.validarJson(httpResponse.body());
        System.out.println(httpResponse.body());

    }

    @And("un jwt token invalido")
    public void unJwtTokenInvalido() throws URISyntaxException, IOException, InterruptedException {

        jwtToken = BearerTokenGenerator.generateToken() + "aa";

    }

    @Then("se recibe mensaje informando error")
    public void seRecibeMensajeInformandoError() throws IOException {

        JsonValidator.validarJson(httpResponse.body());
        System.out.println(httpResponse.body());

    }

    @When("se envia una solicitud de clave de usuario sin el header Authorization")
    public void seEnviaUnaSolicitudDeClaveDeUsuarioSinElHeaderAuthorization() throws URISyntaxException, IOException, InterruptedException {

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(usuario);

        putRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI("http://localhost:18082/usuarios/actualizarContraseña/"+idUsuario))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        httpResponse = httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());

    }
}
