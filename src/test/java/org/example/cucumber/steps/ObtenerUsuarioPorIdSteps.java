package org.example.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.cucumber.validation.JsonValidator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ObtenerUsuarioPorIdSteps {
    String idUsuario;

    HttpRequest getRequest;
    HttpClient httpClient = HttpClient.newHttpClient();
    static HttpResponse<String> httpResponse;


    @Given("el id de un usuario válido")
    public void dadoElId() {
        idUsuario = "10";
    }

    @When("se envía una solicitud de obtener usuario")
    public void seEnviaUnaSolicitudDeObtenerUsuario() throws URISyntaxException, IOException, InterruptedException {


        Dotenv dotenv = Dotenv.load();

        String ipAdress = dotenv.get("target_ip");
        ipAdress += ":18082";

        getRequest = HttpRequest.newBuilder()
                .uri(new URI("http://"+ipAdress+"/usuarios/"+idUsuario))
                .build();
        httpResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

    }

    @Then("se recibe un json del usuario")
    public void seRecibeUnJsonDelUsuario() throws IOException {
        System.out.println(httpResponse.body());
        JsonValidator.validarJson(httpResponse.body());
    }

    @Given("un id que no corresponde a un usuario")
    public void unIdQueNoCorrespondeAUnUsuario() {
        idUsuario = "1000";
    }

    @Then("se recibe un error del servidor")
    public void seRecibeUnErrorDelServidor() throws IOException {
        System.out.println(httpResponse.body());
        JsonValidator.validarJson(httpResponse.body());
    }
}
