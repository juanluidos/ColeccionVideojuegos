package com.coleccion.videojuegos.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.coleccion.videojuegos.entity.Enums.RoleEnum;
import com.coleccion.videojuegos.repository.UserRepository;
import com.coleccion.videojuegos.repository.VideojuegoRepository;

@Component
public class AuthorizationUtils {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    public boolean isOwner(Integer videojuegoId, String username) {
        return videojuegoRepository.findById(videojuegoId)
            .map(videojuego -> videojuego.getUsuario().getUsername().equals(username))
            .orElse(false);
    }

    public boolean isAdmin(String username) {
        return userRepository.findUserByUsername(username)
            .map(usuario -> usuario.getRoles().stream().anyMatch(rol -> rol.getRole().equals(RoleEnum.ADMIN)))
            .orElse(false);
    }
}
