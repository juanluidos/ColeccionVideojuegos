package com.coleccion.videojuegos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.entity.CustomUserDetails;
import com.coleccion.videojuegos.entity.Usuario;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.service.UserService;
import com.coleccion.videojuegos.service.VideojuegosService;
import com.coleccion.videojuegos.web.requests.VideojuegoCompletoRequest;
import com.coleccion.videojuegos.web.requests.VideojuegoRequest;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/videojuegos")
public class VideojuegoController {

    @Autowired
    private VideojuegosService videojuegosService;

    @Autowired
    private UserService userService;

    /** 🔹 Obtener TODOS los videojuegos (Solo Admins) **/
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Videojuego> getTodosLosVideojuegos() throws Exception {
        return videojuegosService.listAllVideojuegos();
    }

    /** 🔹 Obtener los videojuegos del usuario autenticado **/
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mis-videojuegos")
    public List<Videojuego> getMisVideojuegos() {
        Usuario usuario = userService.getAuthenticatedUser();
        System.out.println("Usuario autenticado: " + usuario.getUsername()); // Debug
        return videojuegosService.getVideojuegosByUsuario(usuario);
    }

    /** 🔹 Obtener un videojuego por ID (Solo Admin o dueño del videojuego) **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isOwner(#id, authentication.name)")
    @GetMapping("/{id}")
    public Videojuego getVideojuego(@PathVariable("id") Integer id) throws Exception {
        return videojuegosService.getVideojuego(id);
    }

    /** 🔹 Crear un nuevo videojuego (Solo Usuarios autenticados) **/
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/new")
    public Videojuego crearVideojuego(@RequestBody VideojuegoCompletoRequest vRequest, Authentication authentication) throws Exception {
        return videojuegosService.newVideojuego(vRequest, authentication.getName());
    }

    /** 🔹 Editar un videojuego (Solo Admin o dueño del videojuego) **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isOwner(#id, authentication.name)")
    @PutMapping("/{id}/change")
    public Videojuego editarVideojuego(@PathVariable("id") Integer id, @RequestBody VideojuegoRequest vRequest)
            throws Exception {
        return videojuegosService.updateVideojuego(id, vRequest);
    }

    /** 🔹 Eliminar un videojuego (Solo Admin o dueño del videojuego) **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isOwner(#id, authentication.name)")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteVideojuego(@PathVariable("id") Integer id) throws Exception {
        videojuegosService.deleteVideojuego(id);
        return ResponseEntity.status(HttpStatus.OK).body("Videojuego con id " + id + " eliminado correctamente");
    }
}
