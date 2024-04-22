package com.coleccion.videojuegos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.service.ProgresoService;
import com.coleccion.videojuegos.service.SoporteService;
import com.coleccion.videojuegos.service.VideojuegosService;
import com.coleccion.videojuegos.web.requests.ProgresoRequest;
import com.coleccion.videojuegos.web.requests.SoporteRequest;
import com.coleccion.videojuegos.web.requests.VideojuegoCompletoRequest;
import com.coleccion.videojuegos.web.requests.VideojuegoRequest;

import org.springframework.web.bind.annotation.PutMapping;



@RestController
@CrossOrigin(origins = { "*" })
public class APIController {

	@Autowired
	private ProgresoService progresoService;

	@Autowired
	private SoporteService soporteService;

	@Autowired
	private VideojuegosService videojuegosService;


	@GetMapping("/prueba")
	public String getPruebas() {
		return "Hola";
	}

	/** Videojuegos **/
	
	@GetMapping("/api/v1/videojuegos")
	public List<Videojuego> getVideojuegos() throws Exception {
		return videojuegosService.listAllVideojuegos();
	}

	@GetMapping("/api/v1/videojuegos/{id}")
	public Videojuego getVideojuego(@PathVariable("id") Integer id) throws Exception {
		return videojuegosService.getVideojuego(id);
	}

	@PostMapping("/api/v1/videojuegos/new")
	public Videojuego crearVideojuego (@RequestBody VideojuegoCompletoRequest vRequest) throws Exception{

		return videojuegosService.newVideojuego(vRequest);

	}
	//para updatear solo el apartado videojuego 
	@PutMapping("/api/v1/videojuegos/{id}/change")
	public Videojuego editarVideojuego (@PathVariable("id") Integer id, @RequestBody VideojuegoRequest vRequest) throws Exception {
		
		return videojuegosService.updateVideojuego(id, vRequest);
	
	}

	@DeleteMapping("/api/v1/videojuegos/{id}/delete")
	public ResponseEntity<String> deleteVideojuego(@PathVariable("id") Integer id) throws Exception {
		videojuegosService.deleteVideojuego(id);

		return ResponseEntity.status(HttpStatus.OK).body("Videojuego con id " + id + " eliminado correctamente");
	}


	/** Soporte **/

	@GetMapping("/api/v1/soporte/{idVideojuego}")
	public List<Soporte> getSoporte(@PathVariable("idVideojuego") Integer idVideojuego) throws Exception {
		return videojuegosService.getSoporteListByVideojuego(idVideojuego);
	}

	@PostMapping("/api/v1/soporte/{idVideojuego}/new")
	public Soporte crearSoporte (@PathVariable("idVideojuego") Integer idVideojuego, @RequestBody SoporteRequest sRequest) throws Exception{

		System.out.println(sRequest);
		return soporteService.newSoporte(idVideojuego, sRequest);

	}
	
	//para updatear solo el apartado soporte 
	@PutMapping("/api/v1/soporte/{idVideojuego}/{idSoporte}/change")
	public Soporte editarSoporte (@PathVariable("idVideojuego") Integer idVideojuego, @PathVariable("idSoporte") Integer idSoporte, @RequestBody SoporteRequest sRequest) throws Exception {
		
		return soporteService.updateSoporte(idVideojuego, idSoporte, sRequest);
	
	}

	@DeleteMapping("/api/v1/soporte/{idVideojuego}/{idSoporte}/delete")
	public ResponseEntity<String> deleteSoporte(@PathVariable("idVideojuego") Integer idVideojuego, @PathVariable("idSoporte") Integer idSoporte) throws Exception {
		
		soporteService.deleteSoporte(idSoporte);

		return ResponseEntity.status(HttpStatus.OK).body("Soporte con id " + idSoporte + " eliminado correctamente");
	}


	
	/** Progreso  **/

	@GetMapping("/api/v1/progreso/{idVideojuego}")
	public List<Progreso> getProgreso(@PathVariable("idVideojuego") Integer idVideojuego) throws Exception {
		return videojuegosService.getProgresoListByVideojuego(idVideojuego);
	}

	@PostMapping("/api/v1/progreso/{idVideojuego}/new")
	public Progreso crearProgreso (@PathVariable("idVideojuego") Integer idVideojuego, @RequestBody ProgresoRequest pRequest) throws Exception{

		return progresoService.newProgreso(idVideojuego, pRequest);

	}

	//para updatear solo el apartado progreso 
	@PutMapping("/api/v1/progreso/{idVideojuego}/{idProgreso}/change")
	public Progreso editarProgreso (@PathVariable("idVideojuego") Integer idVideojuego, @PathVariable("idProgreso") Integer idProgreso, @RequestBody ProgresoRequest pRequest) throws Exception {
		
		return progresoService.updateProgreso(idVideojuego, idProgreso, pRequest);
	
	}

	@DeleteMapping("/api/v1/progreso/{idVideojuego}/{idProgreso}/delete")
	public ResponseEntity<String> deleteProgreso(@PathVariable("idVideojuego") Integer idVideojuego, @PathVariable("idProgreso") Integer idProgreso) throws Exception {
		
		progresoService.deleteProgreso(idProgreso);

		return ResponseEntity.status(HttpStatus.OK).body("Progreso con id " + idProgreso + " eliminado correctamente");
	}
}