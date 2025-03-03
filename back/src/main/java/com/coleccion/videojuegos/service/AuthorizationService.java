package com.coleccion.videojuegos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coleccion.videojuegos.entity.Videojuego;

@Service
public class AuthorizationService {

    @Autowired
    private VideojuegosService videojuegosService;

    public boolean isOwner(Integer videojuegoId, String username) {
        Videojuego videojuego = videojuegosService.getVideojuego(videojuegoId);
        return videojuego != null && videojuego.getUsuario().getUsername().equals(username);
    }    
}

