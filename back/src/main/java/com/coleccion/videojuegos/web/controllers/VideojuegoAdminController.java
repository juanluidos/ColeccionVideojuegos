package com.coleccion.videojuegos.web.controllers;

import com.coleccion.videojuegos.service.VideojuegosAdminService;
import com.coleccion.videojuegos.web.dto.VideojuegoAdminDTO;
import com.coleccion.videojuegos.web.requests.VideojuegoCompletoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/videojuegos")
@PreAuthorize("hasRole('ADMIN')") // ✅ Todos los endpoints solo accesibles para admins
public class VideojuegoAdminController {

    @Autowired
    private VideojuegosAdminService videojuegosService;

    /** ✅ 1. Obtener TODOS los videojuegos (con información del usuario dueño) **/
    @GetMapping
    public ResponseEntity<List<VideojuegoAdminDTO>> getAllVideojuegos() {
        List<VideojuegoAdminDTO> videojuegos = videojuegosService.getAllVideojuegos();
        return ResponseEntity.ok(videojuegos);
    }

    /** ✅ 2. Obtener un videojuego por ID (con información del usuario dueño) **/
    @GetMapping("/{id}")
    public ResponseEntity<VideojuegoAdminDTO> getVideojuegoById(@PathVariable Integer id) {
        VideojuegoAdminDTO videojuego = videojuegosService.getVideojuego(id);
        return ResponseEntity.ok(videojuego);
    }

    /** ✅ 3. Obtener todos los videojuegos de un usuario **/
    @GetMapping("/usuario/{username}")
    public ResponseEntity<List<VideojuegoAdminDTO>> getVideojuegosByUsuario(@PathVariable String username) {
        List<VideojuegoAdminDTO> videojuegos = videojuegosService.getVideojuegosByUsuario(username);
        return ResponseEntity.ok(videojuegos);
    }

    /** ✅ 4. Crear un videojuego para un usuario específico **/
    @PostMapping("/usuario/{username}/crear")
    public ResponseEntity<VideojuegoAdminDTO> crearVideojuegoParaUsuario(
            @PathVariable String username, 
            @RequestBody VideojuegoCompletoRequest vRequest) {
        
        VideojuegoAdminDTO nuevoVideojuego = videojuegosService.newVideojuegoParaUsuario(username, vRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVideojuego);
    }

    /** ✅ 5. Editar un videojuego de cualquier usuario (sin necesidad de verificación de dueño) **/
    @PutMapping("/{id}/editar")
    public ResponseEntity<VideojuegoAdminDTO> editarVideojuego(
            @PathVariable Integer id,
            @RequestBody VideojuegoCompletoRequest vRequest) {
        VideojuegoAdminDTO actualizado = videojuegosService.updateVideojuego(id, vRequest);
        return ResponseEntity.ok(actualizado);
    }

    /** ✅ 6. Eliminar un videojuego de cualquier usuario **/
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<String> deleteVideojuego(@PathVariable Integer id) {
        videojuegosService.deleteVideojuego(id);
        return ResponseEntity.ok("Videojuego con ID " + id + " eliminado correctamente.");
    }
}