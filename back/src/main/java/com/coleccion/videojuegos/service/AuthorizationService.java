package com.coleccion.videojuegos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.repository.VideojuegoRepository;

import java.util.Optional;

@Service
public class AuthorizationService {

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    /**   Verifica si el usuario autenticado es dueño del videojuego **/
    public boolean isOwner(Integer id, String username) {
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(id);

        return videojuegoOpt.map(videojuego -> videojuego.getUsuario().getUsername().equals(username))
                            .orElse(false);
    }
}
