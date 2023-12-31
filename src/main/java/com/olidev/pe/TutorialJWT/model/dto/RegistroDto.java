package com.olidev.pe.TutorialJWT.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDto {
    String email;
    String password;
    String nombre;
    String apellido;
    String pais;
    String rol;
}
