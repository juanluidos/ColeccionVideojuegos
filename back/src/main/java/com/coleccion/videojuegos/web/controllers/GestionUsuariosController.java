package com.coleccion.videojuegos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.entity.Usuario;
import com.coleccion.videojuegos.service.UserService;
import com.coleccion.videojuegos.web.controllers.dto.AuthCreateUserRequest;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class GestionUsuariosController {

    @Autowired
    private UserService userService;

    /**   Crear nuevo usuario (solo ADMIN) **/
    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public ResponseEntity<Usuario> createUser(@RequestBody AuthCreateUserRequest userRequest,
                                              @AuthenticationPrincipal Usuario adminUser) {
        Usuario newUser = userService.createUser(userRequest, adminUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**   Obtener todos los usuarios **/
    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public ResponseEntity<List<Usuario>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**   Obtener un usuario por ID **/
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    /**   Eliminar usuario (solo ADMIN) **/
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id,
                                             @AuthenticationPrincipal Usuario adminUser) {
        userService.deleteUser(id, adminUser);
        return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente");
    }

    /**   Actualizar roles de un usuario (solo ADMIN) **/
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/roles")
    public ResponseEntity<Usuario> updateUserRoles(@PathVariable Integer id,
                                                   @RequestBody List<String> roles,
                                                   @AuthenticationPrincipal Usuario adminUser) {
        Usuario updatedUser = userService.updateUserRoles(id, roles, adminUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
