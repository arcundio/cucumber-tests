package org.example.cucumber.model;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UsuarioModel {


    private String firstName;

    private String lastName;

    private String email;

    private String contrasena;  

    
}

