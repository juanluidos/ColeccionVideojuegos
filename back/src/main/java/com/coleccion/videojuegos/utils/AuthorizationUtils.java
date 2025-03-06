package com.coleccion.videojuegos.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.service.VideojuegosService;

@Component
public class AuthorizationUtils {

    @Autowired
    private VideojuegosService videojuegosService;

    public boolean isOwner(Integer videojuegoId, String username) {
        Videojuego videojuego = videojuegosService.getVideojuego(videojuegoId);
        return videojuego != null && videojuego.getUsuario().getUsername().equals(username);
    }
}
