package com.coleccion.videojuegos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.service.ProgresoService;
import com.coleccion.videojuegos.service.VideojuegosService;
import com.coleccion.videojuegos.web.requests.ProgresoRequest;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/progreso")
public class ProgresoController {

    @Autowired
    private ProgresoService progresoService;
    
    @Autowired
    private VideojuegosService videojuegosService;

    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @GetMapping("/{idVideojuego}")
    public List<Progreso> getProgreso(@PathVariable("idVideojuego") Integer idVideojuego) throws Exception {
        return videojuegosService.getProgresoListByVideojuego(idVideojuego);
    }

    @PreAuthorize("hasRole('USER') and @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @PostMapping("/{idVideojuego}/new")
    public Progreso crearProgreso(@PathVariable("idVideojuego") Integer idVideojuego,
                                  @RequestBody ProgresoRequest pRequest) throws Exception {
        return progresoService.newProgreso(idVideojuego, pRequest);
    }

    @PreAuthorize("hasRole('USER') and @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @PutMapping("/{idVideojuego}/{idProgreso}/change")
    public Progreso editarProgreso(@PathVariable("idVideojuego") Integer idVideojuego,
                                   @PathVariable("idProgreso") Integer idProgreso,
                                   @RequestBody ProgresoRequest pRequest) throws Exception {
        return progresoService.updateProgreso(idVideojuego, idProgreso, pRequest);
    }

    @PreAuthorize("hasRole('USER') and @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @DeleteMapping("/{idVideojuego}/{idProgreso}/delete")
    public ResponseEntity<String> deleteProgreso(@PathVariable("idVideojuego") Integer idVideojuego,
                                                 @PathVariable("idProgreso") Integer idProgreso) throws Exception {
        progresoService.deleteProgreso(idProgreso);
        return ResponseEntity.status(HttpStatus.OK).body("Progreso con id " + idProgreso + " eliminado correctamente");
    }
}
