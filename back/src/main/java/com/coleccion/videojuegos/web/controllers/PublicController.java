package com.coleccion.videojuegos.web.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
// @PreAuthorize("permitAll()")
@RequestMapping("/public")
public class PublicController {
	@GetMapping("/prueba")
	public String getMethodName() {
		return "Prueba";
	}
	

}
