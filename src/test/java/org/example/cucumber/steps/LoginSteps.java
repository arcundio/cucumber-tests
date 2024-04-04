package org.example.cucumber.steps;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.cucumber.model.LoginDTO;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class LoginSteps {

    HttpRequest postRequest;
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpResponse<String> httpResponse;
    String email = "sofia@mail.com";
    String password = "sofia789";

    LoginDTO loginDTO = new LoginDTO();

    @Given("un usuario válido con correo electrónico {string} y contraseña {string}")
    public void unUsuarioValidoConCorreoElectronicoYContrasena(String email, String password) throws URISyntaxException {

        loginDTO.setEmail(email);
        loginDTO.setPassword(password);
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(loginDTO);

        Dotenv dotenv = Dotenv.load();

        String ipAdress = dotenv.get("target_ip");
        ipAdress += "18082";

        postRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI("http://"+ipAdress+"/login"))
                .POST(BodyPublishers.ofString(jsonRequest))
                .build();
    }

    @When("se envía una solicitud de inicio de sesión")
    public void seEnviaUnaSolicitudDeInicioDeSesion() throws IOException, InterruptedException {
         httpResponse = httpClient.send(postRequest, BodyHandlers.ofString());
    }

    @Then("se recibe un token JWT válido")
    public void seRecibeUnTokenJWTValido() {

        System.out.println(httpResponse.body());

    }


    @Given("al json le falta el correo o contraseña")
    public void alJsonLeFaltaElCorreoOContrasena() throws URISyntaxException {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword("");
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(loginDTO);

        Dotenv dotenv = Dotenv.load();

        String ipAdress = dotenv.get("target_ip");
        ipAdress += "18082";

        postRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI("http://"+ipAdress+"/login"))
                .POST(BodyPublishers.ofString(jsonRequest))
                .build();

    }

    @Then("se recibe un mensaje informando de la solicitud inválida")
    public void seRecibeUnMensajeInformandoDeLaSolicitudInválida() {
        System.out.println(httpResponse.body());
        Assertions.assertEquals(httpResponse.body(), "Los atributos 'usuario' y 'clave' son obligatorios");
    }


}
