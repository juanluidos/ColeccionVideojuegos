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

import com.coleccion.videojuegos.entity.Videojuego;
import com.coleccion.videojuegos.service.ProgresoService;
import com.coleccion.videojuegos.service.SoporteService;
import com.coleccion.videojuegos.service.VideojuegosService;
import com.coleccion.videojuegos.web.requests.VideojuegoRequest;


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
	public List<Videojuego> getUsuarios() throws Exception {
		return videojuegosService.listAllVideojuegos();
	}
	@PostMapping("/api/v1/videojuegos/new")
	public Videojuego crearVideojuego (@RequestBody VideojuegoRequest vRequest) throws Exception{

		return videojuegosService.newVideojuego(vRequest);

	}

	@DeleteMapping("/api/v1/videojuegos/{id}/delete")
	public ResponseEntity<String> deleteVideojuego(@PathVariable("id") Integer id) throws Exception {
		videojuegosService.deleteVideojuego(id);

		return ResponseEntity.status(HttpStatus.OK).body("Videojuego con id " + id + " eliminado correctamente");
	}
}
