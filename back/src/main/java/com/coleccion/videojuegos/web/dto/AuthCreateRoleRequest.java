package com.coleccion.videojuegos.web.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record AuthCreateRoleRequest(
        @Size(max = 1, message = "El usuario no puede tener más de 1 rol") List<String> roleListName) {
}
