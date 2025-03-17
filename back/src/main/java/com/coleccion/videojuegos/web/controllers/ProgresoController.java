package com.coleccion.videojuegos.web.controllers;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.service.ProgresoService;
import com.coleccion.videojuegos.web.requests.ProgresoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/progreso")
public class ProgresoController {

    @Autowired
    private ProgresoService progresoService;

    /** ✅ Obtener todos los progresos de un videojuego **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @GetMapping("/{idVideojuego}")
    public ResponseEntity<List<Progreso>> getProgreso(@PathVariable("idVideojuego") Integer idVideojuego) {
        return ResponseEntity.ok(progresoService.getProgresoListByVideojuego(idVideojuego));
    }

    /** ✅ Crear un nuevo progreso para un videojuego **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @PostMapping("/{idVideojuego}/new")
    public ResponseEntity<Progreso> crearProgreso(@PathVariable("idVideojuego") Integer idVideojuego,
                                                  @RequestBody ProgresoRequest pRequest) {
        return ResponseEntity.ok(progresoService.newProgreso(idVideojuego, pRequest));
    }

    /** ✅ Editar un progreso existente **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @PutMapping("/{idVideojuego}/{idProgreso}/editar")
    public ResponseEntity<Progreso> editarProgreso(@PathVariable("idVideojuego") Integer idVideojuego,
                                                   @PathVariable("idProgreso") Integer idProgreso,
                                                   @RequestBody ProgresoRequest pRequest) {
        return ResponseEntity.ok(progresoService.updateProgreso(idVideojuego, idProgreso, pRequest));
    }

    /** ✅ Eliminar un progreso **/
    @PreAuthorize("hasRole('ADMIN') or @authorizationUtils.isOwner(#idVideojuego, authentication.name)")
    @DeleteMapping("/{idVideojuego}/{idProgreso}/eliminar")
    public ResponseEntity<String> deleteProgreso(@PathVariable("idVideojuego") Integer idVideojuego,
                                                 @PathVariable("idProgreso") Integer idProgreso) {
        progresoService.deleteProgreso(idProgreso);
        return ResponseEntity.ok("Progreso con ID " + idProgreso + " eliminado correctamente.");
    }
}
