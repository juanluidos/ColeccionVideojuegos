package com.coleccion.videojuegos.service;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.repository.ProgresoRepository;
import com.coleccion.videojuegos.repository.VideojuegoRepository;
import com.coleccion.videojuegos.web.requests.ProgresoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProgresoService {

    @Autowired
    private ProgresoRepository progresoRepository;

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    /** ✅ Obtener lista de progresos de un videojuego */
    public List<Progreso> getProgresoListByVideojuego(Integer idVideojuego) {
        Videojuego videojuego = videojuegoRepository.findById(idVideojuego)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado"));
        return videojuego.getProgreso();
    }

    /** ✅ Crear nuevo progreso en un videojuego */
    public Progreso newProgreso(Integer idVideojuego, ProgresoRequest pRequest) {
        Videojuego videojuego = videojuegoRepository.findById(idVideojuego)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado"));

        Progreso progreso = Progreso.builder()
                .anyoJugado(pRequest.getAnyoJugado())
                .avance(pRequest.getAvance())
                .horasJugadas(pRequest.getHorasJugadas())
                .completadoCien(pRequest.getCompletadoCien())
                .nota(pRequest.getNota())
                .videojuego(videojuego)
                .build();

        return progresoRepository.save(progreso);
    }

    /** ✅ Editar un progreso existente */
    public Progreso updateProgreso(Integer idVideojuego, Integer idProgreso, ProgresoRequest pRequest) {
        // 1️⃣ Buscar el progreso en la BD
        Progreso progreso = progresoRepository.findById(idProgreso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progreso no encontrado"));
    
        // 2️⃣ Verificar que el progreso pertenece al videojuego indicado
        if (!progreso.getVideojuego().getId().equals(idVideojuego)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El progreso no pertenece al videojuego especificado");
        }
    
        // 3️⃣ Actualizar solo si los valores no son nulos
        if (pRequest.getAnyoJugado() != null) progreso.setAnyoJugado(pRequest.getAnyoJugado());
        if (pRequest.getAvance() != null) progreso.setAvance(pRequest.getAvance());
        if (pRequest.getHorasJugadas() != null) progreso.setHorasJugadas(pRequest.getHorasJugadas());
        if (pRequest.getCompletadoCien() != null) progreso.setCompletadoCien(pRequest.getCompletadoCien());
        if (pRequest.getNota() != null) progreso.setNota(pRequest.getNota());
    
        return progresoRepository.save(progreso);
    }
    

    /** ✅ Eliminar un progreso */
    public void deleteProgreso(Integer idProgreso) {
        if (!progresoRepository.existsById(idProgreso)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Progreso no encontrado");
        }
        progresoRepository.deleteById(idProgreso);
    }
}
