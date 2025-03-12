package com.coleccion.videojuegos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.service.SoporteService;
import com.coleccion.videojuegos.service.VideojuegosUsuarioService;
import com.coleccion.videojuegos.web.requests.SoporteRequest;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/soporte")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;
    
    @Autowired
    private VideojuegosUsuarioService videojuegosService;

    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @GetMapping("/{idVideojuego}")
    public List<Soporte> getSoporte(@PathVariable("idVideojuego") Integer idVideojuego) throws Exception {
        return videojuegosService.getSoporteListByVideojuego(idVideojuego);
    }

    @PreAuthorize("hasRole('USER') and @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @PostMapping("/{idVideojuego}/new")
    public Soporte crearSoporte(@PathVariable("idVideojuego") Integer idVideojuego,
                                @RequestBody SoporteRequest sRequest) throws Exception {
        return soporteService.newSoporte(idVideojuego, sRequest);
    }

    @PreAuthorize("hasRole('USER') and @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @PutMapping("/{idVideojuego}/{idSoporte}/change")
    public Soporte editarSoporte(@PathVariable("idVideojuego") Integer idVideojuego,
                                 @PathVariable("idSoporte") Integer idSoporte,
                                 @RequestBody SoporteRequest sRequest) throws Exception {
        return soporteService.updateSoporte(idVideojuego, idSoporte, sRequest);
    }

    @PreAuthorize("hasRole('USER') and @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @DeleteMapping("/{idVideojuego}/{idSoporte}/delete")
    public ResponseEntity<String> deleteSoporte(@PathVariable("idVideojuego") Integer idVideojuego,
                                                @PathVariable("idSoporte") Integer idSoporte) throws Exception {
        soporteService.deleteSoporte(idSoporte);
        return ResponseEntity.status(HttpStatus.OK).body("Soporte con id " + idSoporte + " eliminado correctamente");
    }
}
