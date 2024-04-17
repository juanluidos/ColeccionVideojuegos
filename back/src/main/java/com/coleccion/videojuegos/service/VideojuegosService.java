package com.coleccion.videojuegos.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.entity.Enums.Avance;
import com.coleccion.videojuegos.entity.Enums.Distribucion;
import com.coleccion.videojuegos.entity.Enums.Edicion;
import com.coleccion.videojuegos.entity.Enums.Estado;
import com.coleccion.videojuegos.entity.Enums.Genero;
import com.coleccion.videojuegos.entity.Enums.Plataforma;
import com.coleccion.videojuegos.entity.Enums.Region;
import com.coleccion.videojuegos.entity.Enums.Tienda;
import com.coleccion.videojuegos.entity.Enums.Tipo;
import com.coleccion.videojuegos.repository.VideojuegoRepository;

import jakarta.transaction.Transactional;

public class VideojuegosService {
	@Autowired
	private VideojuegoRepository videojuegoRepository;
	
	private Videojuego get(Optional<Videojuego> videojuego){
		return videojuego.isPresent() ? videojuego.get() : null;
	}

	public Videojuego getVideojuego(Integer id) {
		Optional<Videojuego> videojuego = videojuegoRepository.findById(id);
		return get(videojuego);
	}

	public void deleteVideojuego(Integer id){
		videojuegoRepository.deleteById(id);
	}
	@Transactional
	public Videojuego saveVideojuego(Videojuego videojuego, String nombre, float precio, Date fechaLanzamiento, Date fechaCompra,
	Plataforma plataforma, Genero genero, Tipo tipo, Estado estado, Edicion edicion, Distribucion distribucion, Boolean precintado, 
	Region region, Integer anyoSalidaDist, Tienda tienda, Integer anyoJugado, Avance avance, float horasJugadas, Boolean completadoCien,
	float nota) {

		try {
			videojuego.setNombre(nombre);
			videojuego.setPrecio(precio);
			videojuego.setFechaLanzamiento(fechaLanzamiento);
			videojuego.setFechaCompra(fechaCompra);
			videojuego.setPlataforma(plataforma);
			videojuego.setGenero(genero);

			//si existen datos de soporte o progreso llamar a una funcion de crear soporte crear progreso:
			//Si PROGRESO
			//if()
			//Progreso conexion = conexionesRepository.findById(conex).get();
			//Soporte naturaleza = naturalezaRepository.findById(nat).get();

			return videojuego;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
