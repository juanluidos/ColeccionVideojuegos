package com.coleccion.videojuegos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Enums.Avance;
import com.coleccion.videojuegos.repository.ProgresoRepository;


public class ProgresoService {
	@Autowired
	private ProgresoRepository progresoRepository;
	
	private Progreso get(Optional<Progreso> progreso){
		return progreso.isPresent() ? progreso.get() : null;
	}

	public Progreso getProgreso(Integer id) {
		Optional<Progreso> progreso = progresoRepository.findById(id);
		return get(progreso);
	}

	public void deleteProgreso(Integer id){
		progresoRepository.deleteById(id);
	}

	public Progreso saveProgreso(Progreso progreso, Integer anyoJugado, Avance avance, float horasJugadas, Boolean completadoCien, float nota){
		try{

			progreso.setAnyoJugado(anyoJugado);
			progreso.setAvance(avance);
			progreso.setHorasJugadas(horasJugadas);
			progreso.setCompletadoCien(completadoCien);
			progreso.setNota(nota);

			return progreso;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}