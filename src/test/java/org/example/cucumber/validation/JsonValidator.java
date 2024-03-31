package org.example.cucumber.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.cucumber.java.en.Then;
import org.json.JSONObject;

import java.io.*;
import java.util.Set;

public class JsonValidator {


    public static void validarJson(String httpResponseBody) throws IOException {

        crearArchivoJson(httpResponseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

        File jsonFile = new File("src/test/java/org/example/cucumber/jsonfiles/target.json");
        InputStream jsonStream = Files.asByteSource(jsonFile).openStream();
        File schemaFile = new File("src/test/java/org/example/cucumber/jsonschemas/api-response-schema.json");
        InputStream schemaStream = Files.asByteSource(schemaFile).openStream();


        JsonNode json = objectMapper.readTree(jsonStream);
        JsonSchema schema = schemaFactory.getSchema(schemaStream);

        Set<ValidationMessage> validationResult = schema.validate(json);

        if (validationResult.isEmpty()) {
            System.out.println("There are no validation errors");
        } else {
            validationResult.forEach(vm -> System.out.println(vm.getMessage()));
        }

    }

    private static void crearArchivoJson(String httpResponseBody) throws IOException {

        JSONObject jsonObject = new JSONObject(httpResponseBody);
        String rutaArchivo = "src/test/java/org/example/cucumber/jsonfiles/target.json";
        FileWriter fileWriter = new FileWriter(rutaArchivo);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        jsonObject.write(bufferedWriter);
        bufferedWriter.close();
        System.out.println();
    }

}
