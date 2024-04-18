package com.coleccion.videojuegos.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.coleccion.videojuegos.repository.SoporteRepository;
import com.coleccion.videojuegos.repository.VideojuegoRepository;
import com.coleccion.videojuegos.web.requests.VideojuegoRequest;

import jakarta.transaction.Transactional;
@Service
public class VideojuegosService {
	@Autowired
	private VideojuegoRepository videojuegoRepository;
	@Autowired
	private SoporteService soporteService;
	@Autowired
	private ProgresoService progresoService;
	
	private Videojuego get(Optional<Videojuego> videojuego){
		return videojuego.isPresent() ? videojuego.get() : null;
	}

	public List<Videojuego> listAllVideojuegos() {
        return (List<Videojuego>) videojuegoRepository.findAll();
    }

	public Videojuego getVideojuego(Integer id) {
		Optional<Videojuego> videojuego = videojuegoRepository.findById(id);
		return get(videojuego);
	}

	public void deleteVideojuego(Integer id){
		videojuegoRepository.deleteById(id);
	}
	@Transactional
	public Videojuego newVideojuego(VideojuegoRequest vRequest) throws Exception{
		Videojuego videojuego = new Videojuego();
		try {
			videojuego.setNombre(vRequest.getNombre());
			videojuego.setPrecio(vRequest.getPrecio());
			videojuego.setFechaLanzamiento(vRequest.getFechaLanzamiento());
			videojuego.setFechaCompra(vRequest.getFechaCompra());
			videojuego.setPlataforma(vRequest.getPlataforma());
			videojuego.setGenero(vRequest.getGenero());

			//si existen datos de progreso o soporte llamar a una funcion de crear progreso crear soporte:
			if (vRequest.getAnyoJugado() != null || vRequest.getAvance() != null || vRequest.getHorasJugadas() != null || vRequest.getCompletadoCien() != null || vRequest.getNota() != null) {
				// Código a ejecutar si al menos uno de los valores no es null			
				progresoService.newProgreso(new Progreso(), vRequest.getAnyoJugado(), vRequest.getAvance(), vRequest.getHorasJugadas().floatValue(), vRequest.getCompletadoCien(), vRequest.getNota().floatValue());
			}
			else if(vRequest.getTipo() != null || vRequest.getEstado() != null || vRequest.getEdicion() != null || vRequest.getDistribucion() != null 
				|| vRequest.getPrecintado() != null || vRequest.getRegion() != null || vRequest.getAnyoSalidaDist() != null || vRequest.getTienda() != null){
					
				soporteService.newSoporte(new Soporte(), vRequest.getTipo(), vRequest.getEstado(), vRequest.getEdicion(), vRequest.getDistribucion(), vRequest.getPrecintado(), vRequest.getRegion(),  vRequest.getAnyoSalidaDist(), vRequest.getTienda());

			}
			videojuegoRepository.save(videojuego);
		}
		catch(Exception e){
			throw e;
		}
		return videojuego;
	}
}

