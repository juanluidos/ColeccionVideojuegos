package com.coleccion.videojuegos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.entity.Enums.Edicion;
import com.coleccion.videojuegos.entity.Enums.Estado;
import com.coleccion.videojuegos.entity.Enums.Region;
import com.coleccion.videojuegos.entity.Enums.Tienda;
import com.coleccion.videojuegos.entity.Enums.Tipo;
import com.coleccion.videojuegos.entity.Enums.Distribucion;
import com.coleccion.videojuegos.repository.SoporteRepository;
import com.coleccion.videojuegos.repository.VideojuegoRepository;
import com.coleccion.videojuegos.web.requests.SoporteRequest;
@Service
public class SoporteService {
	@Autowired
	private SoporteRepository soporteRepository;
	
	@Autowired
    private VideojuegoRepository videojuegoRepository;

	private Soporte get(Optional<Soporte> soporte){
		return soporte.isPresent() ? soporte.get() : null;
	}

	private Videojuego getV(Optional<Videojuego> videojuego){
		return videojuego.isPresent() ? videojuego.get() : null;
	}

	public Soporte getSoporte(Integer id) {
		Optional<Soporte> soporte = soporteRepository.findById(id);
		return get(soporte);
	}
	
	public List<Soporte> getSoporteListByVideojuego(Integer id) {
		Optional<List<Soporte>> soporteList = videojuegoRepository.findSoporteListById(id);
		return  soporteList.isPresent() ? soporteList.get() : null;
	}

	public void deleteSoporte(Integer id){
		soporteRepository.deleteById(id);
	}

	public Soporte newSoporteCompleto(Soporte soporte, Tipo tipo, Estado estado, Edicion edicion, Distribucion distribucion, Boolean precintado, Region region, Integer anyoSalidaDist, Tienda tienda) throws Exception{
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
			throw new Exception(e);
		}
		return soporte;
	}

	public Soporte newSoporte(Integer idVideojuego, SoporteRequest sRequest) throws Exception{
		Soporte soporte = new Soporte();
		try{
			soporte.setTipo(sRequest.getTipo());
			soporte.setEstado(sRequest.getEstado());
			soporte.setEdicion(sRequest.getEdicion());
			soporte.setDistribucion(sRequest.getDistribucion());
			soporte.setPrecintado(sRequest.getPrecintado());
			soporte.setRegion(sRequest.getRegion());
			soporte.setAnyoSalidaDist(sRequest.getAnyoSalidaDist());
			soporte.setTienda(sRequest.getTienda());
			Optional<Videojuego> videojuego = videojuegoRepository.findById(idVideojuego);
			soporte.setVideojuego(getV(videojuego));

			soporteRepository.save(soporte);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return soporte;
	}

	
	public Soporte updateSoporte(Integer idVideojuego, Integer idSoporte, SoporteRequest sRequest) throws Exception {
		
		Optional<Soporte> soporteOptional = soporteRepository.findById(idVideojuego);
		if (!soporteOptional.isPresent()) {
			throw new Exception("Soporte no encontrado con el ID videojuego: " + idVideojuego);
		}
	
		Soporte soporte = soporteOptional.get();
		boolean needUpdate = false;
	
		// Compara y actualiza el tipo si es necesario
		if (sRequest.getTipo() != null && !sRequest.getTipo().equals(soporte.getTipo())) {
			soporte.setTipo(sRequest.getTipo());
			needUpdate = true;
		}
		if (sRequest.getEstado() != null && !sRequest.getEstado().equals(soporte.getEstado())) {
			soporte.setEstado(sRequest.getEstado());
			needUpdate = true;
		}
		if (sRequest.getEdicion() != null && !sRequest.getEdicion().equals(soporte.getEdicion())) {
			soporte.setEdicion(sRequest.getEdicion());
			needUpdate = true;
		}
		if (sRequest.getDistribucion() != null && !sRequest.getDistribucion().equals(soporte.getDistribucion())) {
			soporte.setDistribucion(sRequest.getDistribucion());
			needUpdate = true;
		}
		if (sRequest.getPrecintado() != null && !sRequest.getPrecintado().equals(soporte.getPrecintado())) {
			soporte.setPrecintado(sRequest.getPrecintado());
			needUpdate = true;
		}
		if (sRequest.getRegion() != null && !sRequest.getRegion().equals(soporte.getRegion())) {
			soporte.setRegion(sRequest.getRegion());
			needUpdate = true;
		}
		if (sRequest.getAnyoSalidaDist() != null && !sRequest.getAnyoSalidaDist().equals(soporte.getAnyoSalidaDist())) {
			soporte.setAnyoSalidaDist(sRequest.getAnyoSalidaDist());
			needUpdate = true;
		}
		if (sRequest.getTienda() != null && !sRequest.getTienda().equals(soporte.getTienda())) {
			soporte.setTienda(sRequest.getTienda());
			needUpdate = true;
		}

		// Finalmente, si es necesario actualizar, guardamos el soporte en la base de datos
		if (needUpdate) {
			soporte = soporteRepository.save(soporte);
		}
	
		return soporte;
	}

}
