package com.coleccion.videojuegos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.service.VideojuegosService;
import com.coleccion.videojuegos.utils.AuthorizationUtils;
import com.coleccion.videojuegos.web.requests.VideojuegoCompletoRequest;
import com.coleccion.videojuegos.web.requests.VideojuegoRequest;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/videojuegos")
public class VideojuegoUsuarioController {

    @Autowired
    private VideojuegosService videojuegosService;

    @Autowired
    private AuthorizationUtils authorizationUtils;

    /** ✅ Obtener los videojuegos del usuario autenticado **/
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mis-videojuegos")
    public ResponseEntity<List<Videojuego>> getMisVideojuegos(Authentication authentication) {
        List<Videojuego> misVideojuegos = videojuegosService.getVideojuegosByUsuario(authentication.getName());
        return ResponseEntity.ok(misVideojuegos);
    }

    /** ✅ Obtener un videojuego por ID (solo si es dueño) **/
    @PreAuthorize("@authorizationUtils.isOwner(#id, authentication.name)")
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> getVideojuego(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(videojuegosService.getVideojuego(id));
    }

    /** ✅ Crear un nuevo videojuego **/
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/new")
    public ResponseEntity<Videojuego> crearVideojuego(@RequestBody VideojuegoCompletoRequest vRequest, Authentication authentication) {
        Videojuego nuevoVideojuego = videojuegosService.newVideojuego(vRequest, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVideojuego);
    }
    

    /** ✅ Editar un videojuego (solo si es dueño) **/
    // No usamos el preauthorize porque antes necesitamos saber si el videojuego existe o no, para mejorar la respuesta al cliente
    @PutMapping("/{id}/editar")
    public ResponseEntity<?> editarVideojuego(@PathVariable("id") Integer id, 
                                              @RequestBody VideojuegoCompletoRequest vRequest, 
                                              Authentication authentication) {
        // Verificamos si el videojuego existe antes de comprobar permisos
        Videojuego videojuego = videojuegosService.getVideojuego(id);
        
        if (videojuego == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Videojuego no encontrado");
        }
    
        // Ahora sí verificamos si el usuario es dueño antes de actualizarlo
        if (!authorizationUtils.isOwner(id, authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar este videojuego.");
        }
    
        // 🔹 Llamamos al service pasando el username del usuario autenticado
        Videojuego actualizado = videojuegosService.updateVideojuego(id, vRequest, authentication.getName());
        return ResponseEntity.ok(actualizado);
    }
    

    /** ✅ Eliminar un videojuego (solo si es dueño) **/
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<?> deleteVideojuego(@PathVariable("id") Integer id, Authentication authentication) {
        // 🔹 Verificamos si el videojuego existe antes de cualquier otra acción
        Videojuego videojuego = videojuegosService.getVideojuego(id);
        
        if (videojuego == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Videojuego no encontrado");
        }

        // 🔹 Verificamos si el usuario autenticado es el dueño
        if (!authorizationUtils.isOwner(id, authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar este videojuego.");
        }

        // 🔹 Eliminamos el videojuego
        videojuegosService.deleteVideojuego(id);
        return ResponseEntity.ok("Videojuego con ID " + id + " eliminado correctamente.");
    }

}
