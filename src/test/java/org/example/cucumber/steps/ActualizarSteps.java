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

public class ActualizarSteps {


    UsuarioModel usuario = new UsuarioModel();
    String idUsuario;
    String jwtToken;
    HttpRequest putRequest;
    HttpClient httpClient = HttpClient.newHttpClient();
    static HttpResponse<String> httpResponse;

    @Given("un json con un usuario valido")
    public void jsonvalido() {

        Faker faker = new Faker();
        idUsuario = "8";
        usuario.setFirstName(faker.name().firstName());
        usuario.setLastName(faker.name().lastName());
        usuario.setEmail(faker.internet().emailAddress());
        
    }

    @And("un Bearer token valido")
    public void unBearerTokenValido() throws URISyntaxException, IOException, InterruptedException {

        jwtToken = BearerTokenGenerator.generateToken();

    }

    @When("se envia una solicitud de actualizar usuario")
    public void seEnviaUnaSolicitudDeActualizarUsuario() throws URISyntaxException, IOException, InterruptedException {

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(usuario);

        putRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+jwtToken)
                .uri(new URI("http://localhost:18082/usuarios/"+idUsuario))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        httpResponse = httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());
        
    }

    @Then("se recibe un mensaje de confirmacion")
    public void seRecibeUnMensajeDeConfirmacion() throws IOException {

        JsonValidator.validarJson(httpResponse.body());
        System.out.println(httpResponse.body());

    }

    @And("un Bearer token invalido")
    public void unBearerTokenInvalido() throws URISyntaxException, IOException, InterruptedException {

        jwtToken = BearerTokenGenerator.generateToken() + "dd";

    }

    @When("se envia una solicitud de actualizar usuario sin el header Authorization")
    public void seEnviaUnaSolicitudDeActualizarUsuarioSinElHeaderAuthorization() throws IOException, InterruptedException, URISyntaxException {

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(usuario);

        putRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI("http://localhost:18082/usuarios/"+idUsuario))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        httpResponse = httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());
    }

    @Then("se recibe un mensaje informando que el token no es valido")
    public void seRecibeUnMensajeInformandoQueElTokenNoEsValido() throws IOException {

        JsonValidator.validarJson(httpResponse.body());
        System.out.println(httpResponse.body());

    }

    @Then("se recibe un mensaje informando que se requiere el header Authorization")
    public void seRecibeUnMensajeInformandoQueSeRequiereElHeaderAuthorization() throws IOException {

        JsonValidator.validarJson(httpResponse.body());
        System.out.println(httpResponse.body());


    }
}
