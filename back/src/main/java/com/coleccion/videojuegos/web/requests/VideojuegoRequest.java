package com.coleccion.videojuegos.web.requests;

import java.sql.Date;
import lombok.Data;

import com.coleccion.videojuegos.entity.Enums.Genero;
import com.coleccion.videojuegos.entity.Enums.Plataforma;

@Data
public class VideojuegoRequest {

	private Integer id;
	private String nombre; 
	private Float precio; 
	private Date fechaLanzamiento;
	private Date fechaCompra;
	private Plataforma plataforma;
	private Genero genero;
}
