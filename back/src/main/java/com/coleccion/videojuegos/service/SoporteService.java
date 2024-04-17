package com.coleccion.videojuegos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.repository.SoporteRepository;

public class SoporteService {
	@Autowired
	private SoporteRepository soporteRepository;
	
	private Soporte get(Optional<Soporte> soporte){
		return soporte.isPresent() ? soporte.get() : null;
	}

	public Soporte getSoporte(Integer id) {
		Optional<Soporte> soporte = soporteRepository.findById(id);
		return get(soporte);
	}

	public void deleteSoporte(Integer id){
		soporteRepository.deleteById(id);
	}
}
