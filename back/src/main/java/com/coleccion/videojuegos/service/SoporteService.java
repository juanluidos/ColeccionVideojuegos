package com.coleccion.videojuegos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.entity.Enums.Edicion;
import com.coleccion.videojuegos.entity.Enums.Estado;
import com.coleccion.videojuegos.entity.Enums.Region;
import com.coleccion.videojuegos.entity.Enums.Tienda;
import com.coleccion.videojuegos.entity.Enums.Tipo;
import com.coleccion.videojuegos.entity.Enums.Distribucion;
import com.coleccion.videojuegos.repository.SoporteRepository;
@Service
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

	public Soporte newSoporte(Soporte soporte, Tipo tipo, Estado estado, Edicion edicion, Distribucion distribucion, Boolean precintado, Region region, Integer anyoSalidaDist, Tienda tienda){
		try{
			soporte.setTipo(tipo);
			soporte.setEstado(estado);
			soporte.setEdicion(edicion);
			soporte.setDistribucion(distribucion);
			soporte.setPrecintado(precintado);
			soporte.setRegion(region);
			soporte.setAnyoSalidaDist(anyoSalidaDist);
			soporte.setTienda(tienda);

			soporteRepository.save(soporte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return soporte;
	}
}
