package com.coleccion.videojuegos.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequest(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank String password,
        @Valid AuthCreateRoleRequest roleRequest
) {}
