package com.coleccion.videojuegos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.service.VideojuegosService;
import com.coleccion.videojuegos.web.requests.VideojuegoCompletoRequest;
import com.coleccion.videojuegos.web.requests.VideojuegoRequest;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/videojuegos")
public class VideojuegoController {

    @Autowired
    private VideojuegosService videojuegosService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<Videojuego> getVideojuegos() throws Exception {
        return videojuegosService.listAllVideojuegos();
    }

    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isOwner(#id, authentication.name)")
    @GetMapping("/{id}")
    public Videojuego getVideojuego(@PathVariable("id") Integer id) throws Exception {
        return videojuegosService.getVideojuego(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/new")
    public Videojuego crearVideojuego(@RequestBody VideojuegoCompletoRequest vRequest) throws Exception {
        return videojuegosService.newVideojuego(vRequest);
    }

    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isOwner(#id, authentication.name)")
    @PutMapping("/{id}/change")
    public Videojuego editarVideojuego(@PathVariable("id") Integer id, @RequestBody VideojuegoRequest vRequest)
            throws Exception {
        return videojuegosService.updateVideojuego(id, vRequest);
    }

    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isOwner(#id, authentication.name)")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteVideojuego(@PathVariable("id") Integer id) throws Exception {
        videojuegosService.deleteVideojuego(id);
        return ResponseEntity.status(HttpStatus.OK).body("Videojuego con id " + id + " eliminado correctamente");
    }
}
