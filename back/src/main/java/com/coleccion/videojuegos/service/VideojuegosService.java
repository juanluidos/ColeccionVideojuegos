package com.coleccion.videojuegos.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.repository.VideojuegoRepository;
import com.coleccion.videojuegos.web.requests.VideojuegoCompletoRequest;
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

	@Transactional
	public Videojuego newVideojuego(VideojuegoCompletoRequest vRequest) throws Exception{
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
				Progreso nuevoProgreso = progresoService.newProgresoCompleto(new Progreso(), vRequest.getAnyoJugado(), vRequest.getAvance(), vRequest.getHorasJugadas().floatValue(), vRequest.getCompletadoCien(), vRequest.getNota().floatValue());
				videojuego.addProgreso(nuevoProgreso);
			}
			if(vRequest.getTipo() != null || vRequest.getEstado() != null || vRequest.getEdicion() != null || vRequest.getDistribucion() != null 
				|| vRequest.getPrecintado() != null || vRequest.getRegion() != null || vRequest.getAnyoSalidaDist() != null || vRequest.getTienda() != null){
					
				Soporte nuevoSoporte = soporteService.newSoporteCompleto(new Soporte(), vRequest.getTipo(), vRequest.getEstado(), vRequest.getEdicion(), vRequest.getDistribucion(), vRequest.getPrecintado(), vRequest.getRegion(),  vRequest.getAnyoSalidaDist(), vRequest.getTienda());
				videojuego.addSoporte(nuevoSoporte);

			}
			videojuegoRepository.save(videojuego);
		}
		catch(Exception e){
			throw new Exception("Error al crear el videojuego: " + e.getMessage());
		}
		return videojuego;
	}

	//para updatear solo el apartado videojuego 
	public Videojuego updateVideojuego(Integer id, VideojuegoRequest vRequest) throws Exception{
		try {
			Optional<Videojuego> videojuegoOpcional = videojuegoRepository.findById(id);
			if (videojuegoOpcional.isPresent()) {
				Videojuego videojuego = videojuegoOpcional.get();
				boolean needUpdate = false;
	
				// Compara y establece el nombre si es diferente y no nulo
				if (vRequest.getNombre() != null && !vRequest.getNombre().equals(videojuego.getNombre())) {
					videojuego.setNombre(vRequest.getNombre());
					needUpdate = true;
				}
				if (vRequest.getPrecio() != null && !vRequest.getPrecio().equals(videojuego.getPrecio())) {
					videojuego.setPrecio(vRequest.getPrecio());
					needUpdate = true;
				}
				if (vRequest.getFechaLanzamiento() != null && !vRequest.getFechaLanzamiento().equals(videojuego.getFechaLanzamiento())) {
					videojuego.setFechaLanzamiento(vRequest.getFechaLanzamiento());
					needUpdate = true;
				}
				if (vRequest.getFechaCompra() != null && !vRequest.getFechaCompra().equals(videojuego.getFechaCompra())) {
					videojuego.setFechaCompra(vRequest.getFechaCompra());
					needUpdate = true;
				}
				if (vRequest.getPlataforma() != null && !vRequest.getPlataforma().equals(videojuego.getPlataforma())) {
					videojuego.setPlataforma(vRequest.getPlataforma());
					needUpdate = true;
				}
				if (vRequest.getGenero() != null && !vRequest.getGenero().equals(videojuego.getGenero())) {
					videojuego.setGenero(vRequest.getGenero());
					needUpdate = true;
				}
	
				// Si hay al menos un cambio, actualiza la entidad
				if (needUpdate) {
					videojuegoRepository.save(videojuego);
				} else{
					throw new Exception("No hay ningún cambio a realizar");
				}
	
				return videojuego;
			} else {
				throw new Exception("No existe dicho videojuego en la base de datos");
			}
			
		} catch(Exception e){
			throw new Exception("Error al actualizar el videojuego: " + e.getMessage());
		}
	}

	public void deleteVideojuego(Integer id) throws Exception{
		try{
			videojuegoRepository.deleteById(id);
		}
		catch(Exception e){
			throw new Exception("Error al eliminar el videojuego: " + e.getMessage());
		}
	}
	
	public List<Soporte> getSoporteListByVideojuego(Integer idVideojuego) {
		Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(idVideojuego);
		if (videojuegoOpt.isPresent()) {
			Videojuego videojuego = videojuegoOpt.get();
			return videojuego.getSoporte();  // Retorna la lista de soportes directamente
		} else {
			return new ArrayList<>();  // Retorna una lista vacía si no se encuentra el videojuego
		}
	}

	public List<Progreso> getProgresoListByVideojuego(Integer idVideojuego) {
		Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(idVideojuego);
		if(videojuegoOpt.isPresent()){
			Videojuego videojuego = videojuegoOpt.get();
			return videojuego.getProgreso();
		}else{
			return new ArrayList<>();
		}
	}
}