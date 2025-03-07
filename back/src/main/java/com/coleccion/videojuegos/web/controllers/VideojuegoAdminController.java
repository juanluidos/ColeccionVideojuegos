package com.coleccion.videojuegos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.service.VideojuegosService;
import com.coleccion.videojuegos.web.requests.VideojuegoRequest;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/admin/videojuegos")
public class VideojuegoAdminController {

    @Autowired
    private VideojuegosService videojuegosService;

    /** ✅ Obtener TODOS los videojuegos (solo Admins) **/
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Videojuego>> getTodosLosVideojuegos() {
        return ResponseEntity.ok(videojuegosService.listAllVideojuegos());
    }

    /** ✅ Obtener un videojuego por ID (cualquier juego) **/
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> getVideojuego(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(videojuegosService.getVideojuego(id));
    }

    /** ✅ Editar un videojuego (solo Admins) **/
    // @PreAuthorize("hasRole('ADMIN')")
    // @PutMapping("/{id}/editar")
    // public ResponseEntity<Videojuego> editarVideojuego(@PathVariable("id") Integer id, @RequestBody VideojuegoRequest vRequest) {
    //     Videojuego actualizado = videojuegosService.updateVideojuego(id, vRequest);
    //     return ResponseEntity.ok(actualizado);
    // }

    /** ✅ Eliminar un videojuego (solo Admins) **/
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<String> deleteVideojuego(@PathVariable("id") Integer id) {
        videojuegosService.deleteVideojuego(id);
        return ResponseEntity.ok("Videojuego con id " + id + " eliminado correctamente por un administrador.");
    }
}
