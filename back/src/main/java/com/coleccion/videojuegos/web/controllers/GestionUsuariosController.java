package com.coleccion.videojuegos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.service.UserService;
import com.coleccion.videojuegos.web.dto.AuthCreateUserRequest;
import com.coleccion.videojuegos.web.dto.UserDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/gestionUsuarios")
public class GestionUsuariosController {

    @Autowired
    private UserService userService;

    /** ✅ Crear nuevo usuario (solo ADMIN) **/
    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public ResponseEntity<?> createUser(@RequestBody AuthCreateUserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    /** ✅ Obtener todos los usuarios **/
    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();
    }

    /** ✅ Obtener un usuario por ID **/
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    /** ✅ Actualizar roles de un usuario (solo ADMIN) **/
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/cambiarRoles")
    public ResponseEntity<?> updateUserRoles(@PathVariable Integer id,
                                             @RequestBody List<String> roles) {
        return userService.updateUserRoles(id, roles);
    }
}
