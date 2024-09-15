package com.coleccion.videojuegos.service;

import java.util.List;
import java.util.Optional;

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
	
	private Progreso get(Optional<Progreso> progreso){
		return progreso.isPresent() ? progreso.get() : null;
	}

	private Videojuego getV(Optional<Videojuego> videojuego){
		return videojuego.isPresent() ? videojuego.get() : null;
	}

	public Progreso getProgreso(Integer id) {
		Optional<Progreso> progreso = progresoRepository.findById(id);
		return get(progreso);
	}

	public List<Progreso> getProgresoListByVideojuego(Integer id) {
		Optional<List<Progreso>> progresoList = videojuegoRepository.findProgresoListById(id);
		return  progresoList.isPresent() ? progresoList.get() : null;
	}

	public void deleteProgreso(Integer id){
		progresoRepository.deleteById(id);
	}

	public Progreso newProgresoCompleto(Progreso progreso, Integer anyoJugado, Avance avance, float horasJugadas, Boolean completadoCien, float nota){
		try{

			progreso.setAnyoJugado(anyoJugado);
			progreso.setAvance(avance);
			progreso.setHorasJugadas(horasJugadas);
			progreso.setCompletadoCien(completadoCien);
			progreso.setNota(nota);
			
			progresoRepository.save(progreso);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return progreso;
	}

	public Progreso newProgreso(Integer idVideojuego, ProgresoRequest pRequest){
		Progreso progreso = new Progreso();
		try{
			progreso.setAnyoJugado(pRequest.getAnyoJugado());
			progreso.setAvance(pRequest.getAvance());
			progreso.setHorasJugadas(pRequest.getHorasJugadas());
			progreso.setCompletadoCien(pRequest.getCompletadoCien());
			progreso.setNota(pRequest.getNota());
			Optional<Videojuego> videojuego = videojuegoRepository.findById(idVideojuego);
			progreso.setVideojuego(getV(videojuego));
			
			progresoRepository.save(progreso);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return progreso;
	}

	public Progreso updateProgreso(Integer idVideojuego, Integer idProgreso, ProgresoRequest pRequest) throws Exception {
	
		Optional<Progreso> progresoOptional = progresoRepository.findById(idProgreso);
		if (!progresoOptional.isPresent()) {
			throw new Exception("Progreso no encontrado con el ID: " + idProgreso);
		}
	
		Progreso progreso = progresoOptional.get();
		boolean needUpdate = false;
	
		// Compara y actualiza el año jugado si es necesario
		if (pRequest.getAnyoJugado() != null && !pRequest.getAnyoJugado().equals(progreso.getAnyoJugado())) {
			progreso.setAnyoJugado(pRequest.getAnyoJugado());
			needUpdate = true;
		}
	
		// Compara y actualiza el avance si es diferente
		if (pRequest.getAvance() != null && !pRequest.getAvance().equals(progreso.getAvance())) {
			progreso.setAvance(pRequest.getAvance());
			needUpdate = true;
		}
	
		// Compara y actualiza las horas jugadas si son diferentes
		if (pRequest.getHorasJugadas() != null && !pRequest.getHorasJugadas().equals(progreso.getHorasJugadas())) {
			progreso.setHorasJugadas(pRequest.getHorasJugadas());
			needUpdate = true;
		}
	
		// Compara y actualiza si se ha completado al cien si es diferente
		if (pRequest.getCompletadoCien() != null && !pRequest.getCompletadoCien().equals(progreso.getCompletadoCien())) {
			progreso.setCompletadoCien(pRequest.getCompletadoCien());
			needUpdate = true;
		}
	
		// Compara y actualiza la nota si es diferente
		if (pRequest.getNota() != null && !pRequest.getNota().equals(progreso.getNota())) {
			progreso.setNota(pRequest.getNota());
			needUpdate = true;
		}
	
		// Si hay al menos un cambio, actualiza la entidad
		if (needUpdate) {
			progreso = progresoRepository.save(progreso);
		}
	
		return progreso;
	}
	
}
