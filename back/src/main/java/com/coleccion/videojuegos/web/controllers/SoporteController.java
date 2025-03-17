package com.coleccion.videojuegos.web.controllers;

import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.service.SoporteService;
import com.coleccion.videojuegos.web.requests.SoporteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/soporte")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    /** ✅ Obtener todas las copias de un videojuego **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @GetMapping("/{idVideojuego}")
    public ResponseEntity<List<Soporte>> getSoporte(@PathVariable("idVideojuego") Integer idVideojuego) {
        return ResponseEntity.ok(soporteService.getSoporteListByVideojuego(idVideojuego));
    }

    /** ✅ Añadir una nueva copia de un videojuego **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @PostMapping("/{idVideojuego}/new")
    public ResponseEntity<Soporte> crearSoporte(@PathVariable("idVideojuego") Integer idVideojuego,
                                                @RequestBody SoporteRequest sRequest) {
        return ResponseEntity.ok(soporteService.newSoporte(idVideojuego, sRequest));
    }

    /** ✅ Editar una copia de un videojuego **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @PutMapping("/{idVideojuego}/{idSoporte}/editar")
    public ResponseEntity<Soporte> editarSoporte(@PathVariable("idVideojuego") Integer idVideojuego,
                                                 @PathVariable("idSoporte") Integer idSoporte,
                                                 @RequestBody SoporteRequest sRequest) {
        return ResponseEntity.ok(soporteService.updateSoporte(idVideojuego, idSoporte, sRequest));
    }

    /** ✅ Eliminar una copia de un videojuego **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @DeleteMapping("/{idVideojuego}/{idSoporte}/eliminar")
    public ResponseEntity<String> deleteSoporte(@PathVariable("idVideojuego") Integer idVideojuego,
                                                @PathVariable("idSoporte") Integer idSoporte) {
        soporteService.deleteSoporte(idSoporte);
        return ResponseEntity.ok("Soporte con ID " + idSoporte + " eliminado correctamente.");
    }
}
