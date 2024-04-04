package org.example.cucumber.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.networknt.schema.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.cucumber.model.UsuarioModel;
import org.example.cucumber.validation.JsonValidator;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;

public class CrearSteps {

    HttpRequest postRequest;
    HttpClient httpClient = HttpClient.newHttpClient();
    static HttpResponse<String> httpResponse;

    UsuarioModel usuario = new UsuarioModel();


    @Given("un DTO con la info necesaria para crear el usuario")
    public void unDTOConLaInfoNecesariaParaCrearElUsuario() {

        Faker faker = new Faker();

        usuario.setFirstName(faker.name().firstName());
        usuario.setLastName(faker.name().lastName());
        usuario.setEmail(faker.internet().emailAddress());
        String fakerPassword = faker.internet().password();
        fakerPassword = fakerPassword.substring(0, Math.min(fakerPassword.length(), 12));
        usuario.setContrasena(fakerPassword);

    }

    @When("se envía una solicitud de crear usuario")
    public void seEnvíaUnaSolicitudDeCrearUsuario() throws URISyntaxException, IOException, InterruptedException {

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(usuario);

        Dotenv dotenv = Dotenv.load();

        String ipAdress = dotenv.get("target_ip");
        ipAdress += ":18082";

        postRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI("http://"+ipAdress+"/usuarios"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        httpResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

    }

    @Then("se recibe un mensaje de confirmación")
    public void seRecibeUnMensajeDeConfirmación() throws IOException {

        System.out.println(httpResponse.body());
        JsonValidator.validarJson(httpResponse.body());
    }



}
