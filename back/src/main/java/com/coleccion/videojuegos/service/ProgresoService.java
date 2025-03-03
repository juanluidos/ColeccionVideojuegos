package com.coleccion.videojuegos.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.entity.Enums.Avance;
import com.coleccion.videojuegos.repository.ProgresoRepository;
import com.coleccion.videojuegos.web.requests.ProgresoRequest;
import com.coleccion.videojuegos.repository.VideojuegoRepository;

@Service
public class ProgresoService {
    @Autowired
    private ProgresoRepository progresoRepository;

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    public Progreso getProgreso(Integer id) {
        return progresoRepository.findById(id).orElse(null);
    }

    public List<Progreso> getProgresoListByVideojuego(Integer idVideojuego) {
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(idVideojuego);
        return videojuegoOpt.map(Videojuego::getProgreso).orElse(new ArrayList<>());
    }

    public void deleteProgreso(Integer id) {
        progresoRepository.deleteById(id);
    }

    public Progreso newProgreso(Integer idVideojuego, ProgresoRequest pRequest) {
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(idVideojuego);
        if (videojuegoOpt.isEmpty()) {
            throw new RuntimeException("El videojuego con ID " + idVideojuego + " no existe.");
        }

        Progreso progreso = Progreso.builder()
                .anyoJugado(pRequest.getAnyoJugado())
                .avance(pRequest.getAvance())
                .horasJugadas(pRequest.getHorasJugadas())
                .completadoCien(pRequest.getCompletadoCien())
                .nota(pRequest.getNota())
                .videojuego(videojuegoOpt.get())
                .build();

        return progresoRepository.save(progreso);
    }

	public Progreso updateProgreso(Integer idVideojuego, Integer idProgreso, ProgresoRequest pRequest) {
		Optional<Progreso> progresoOptional = progresoRepository.findById(idProgreso);
		if (progresoOptional.isEmpty()) {
			throw new RuntimeException("Progreso no encontrado con el ID: " + idProgreso);
		}
	
		Progreso progreso = progresoOptional.get();
	
		// Actualizar solo si los valores no son nulos y han cambiado
		if (pRequest.getAnyoJugado() != null) {
			progreso.setAnyoJugado(pRequest.getAnyoJugado());
		}
		if (pRequest.getAvance() != null) {
			progreso.setAvance(pRequest.getAvance());
		}
		if (pRequest.getHorasJugadas() != null) {
			progreso.setHorasJugadas(pRequest.getHorasJugadas());
		}
		if (pRequest.getCompletadoCien() != null) {
			progreso.setCompletadoCien(pRequest.getCompletadoCien());
		}
		if (pRequest.getNota() != null) {
			progreso.setNota(pRequest.getNota());
		}
	
		return progresoRepository.save(progreso);
	}
	
}
