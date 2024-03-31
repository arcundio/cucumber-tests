package org.example.cucumber.helperclasses;

import com.google.gson.Gson;
import org.example.cucumber.model.LoginDTO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BearerTokenGenerator {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final LoginDTO loginDTO = new LoginDTO();

    public static String generateToken() throws URISyntaxException, IOException, InterruptedException {

        String email = "sofia@mail.com";
        loginDTO.setEmail(email);
        String password = "sofia789";
        loginDTO.setPassword(password);
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(loginDTO);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI("http://localhost:18082/login"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        return httpResponse.body();

    }


}
