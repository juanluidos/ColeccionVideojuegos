package com.coleccion.videojuegos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.coleccion.videojuegos.entity"})
public class VideojuegosApplication{

	public static void main(String[] args) {
		SpringApplication.run(VideojuegosApplication.class, args);
	}

}
