package com.coleccion.videojuegos.web.requests;

import com.coleccion.videojuegos.entity.Enums.Avance;

import lombok.Data;
@Data
public class ProgresoRequest {
	
	private Integer id;
	private Integer anyoJugado;; 
	private Avance avance;
	private Float horasJugadas;
	private Boolean completadoCien;
	private Float nota;

}