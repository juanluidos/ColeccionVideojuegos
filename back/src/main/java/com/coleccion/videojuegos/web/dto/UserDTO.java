package com.coleccion.videojuegos.web.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private List<String> roles;
}
