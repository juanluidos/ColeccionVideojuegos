package com.coleccion.videojuegos.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.repository.SoporteRepository;
import com.coleccion.videojuegos.repository.VideojuegoRepository;
import com.coleccion.videojuegos.web.requests.SoporteRequest;

@Service
public class SoporteService {
    @Autowired
    private SoporteRepository soporteRepository;

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    public Soporte getSoporte(Integer id) {
        return soporteRepository.findById(id).orElse(null);
    }

    public List<Soporte> getSoporteListByVideojuego(Integer idVideojuego) {
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(idVideojuego);
        return videojuegoOpt.map(Videojuego::getSoporte).orElse(new ArrayList<>());
    }

    public Soporte newSoporte(Integer idVideojuego, SoporteRequest sRequest) {
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(idVideojuego);
        if (videojuegoOpt.isEmpty()) {
            throw new RuntimeException("El videojuego con ID " + idVideojuego + " no existe.");
        }

        Soporte soporte = Soporte.builder()
                .tipo(sRequest.getTipo())
                .estado(sRequest.getEstado())
                .edicion(sRequest.getEdicion())
                .distribucion(sRequest.getDistribucion())
                .precintado(sRequest.getPrecintado())
                .region(sRequest.getRegion())
                .anyoSalidaDist(sRequest.getAnyoSalidaDist())
                .tienda(sRequest.getTienda())
                .videojuego(videojuegoOpt.get())
                .build();

        return soporteRepository.save(soporte);
    }

	public void deleteSoporte(Integer id) {
		if (!soporteRepository.existsById(id)) {
			throw new RuntimeException("El soporte con ID " + id + " no existe.");
		}
		soporteRepository.deleteById(id);
	}
	
	public Soporte updateSoporte(Integer idVideojuego, Integer idSoporte, SoporteRequest sRequest) {
		Optional<Soporte> soporteOptional = soporteRepository.findById(idSoporte);
		if (soporteOptional.isEmpty()) {
			throw new RuntimeException("Soporte no encontrado con el ID: " + idSoporte);
		}
	
		Soporte soporte = soporteOptional.get();
	
		// Actualizar solo si los valores no son nulos y han cambiado
		if (sRequest.getTipo() != null) {
			soporte.setTipo(sRequest.getTipo());
		}
		if (sRequest.getEstado() != null) {
			soporte.setEstado(sRequest.getEstado());
		}
		if (sRequest.getEdicion() != null) {
			soporte.setEdicion(sRequest.getEdicion());
		}
		if (sRequest.getDistribucion() != null) {
			soporte.setDistribucion(sRequest.getDistribucion());
		}
		if (sRequest.getPrecintado() != null) {
			soporte.setPrecintado(sRequest.getPrecintado());
		}
		if (sRequest.getRegion() != null) {
			soporte.setRegion(sRequest.getRegion());
		}
		if (sRequest.getAnyoSalidaDist() != null) {
			soporte.setAnyoSalidaDist(sRequest.getAnyoSalidaDist());
		}
		if (sRequest.getTienda() != null) {
			soporte.setTienda(sRequest.getTienda());
		}
	
		return soporteRepository.save(soporte);
	}
	
}
