package com.coleccion.videojuegos.web.requests;

import java.sql.Date;
import java.util.List;
import lombok.Data;

import com.coleccion.videojuegos.entity.Progreso;
import com.coleccion.videojuegos.entity.Soporte;
import com.coleccion.videojuegos.entity.Enums.Avance;
import com.coleccion.videojuegos.entity.Enums.Distribucion;
import com.coleccion.videojuegos.entity.Enums.Edicion;
import com.coleccion.videojuegos.entity.Enums.Estado;
import com.coleccion.videojuegos.entity.Enums.Genero;
import com.coleccion.videojuegos.entity.Enums.Plataforma;
import com.coleccion.videojuegos.entity.Enums.Region;
import com.coleccion.videojuegos.entity.Enums.Tienda;
import com.coleccion.videojuegos.entity.Enums.Tipo;

@Data
public class VideojuegoRequest {

	private Integer id;
	private String nombre; 
	private Float precio; 
	private Date fechaLanzamiento;
	private Date fechaCompra;
	private Plataforma plataforma;
	private Genero genero;
	private List<Progreso> progreso;
	private List<Soporte> soporte;
	private Tipo tipo;
	private Estado estado;
	private Edicion edicion; 
	private Distribucion distribucion;
	private Boolean precintado;
	private Region region; 
	private Integer anyoSalidaDist;
	private Tienda tienda;
	private Integer anyoJugado;
	private Avance avance;
	private Float horasJugadas; 
	private Boolean completadoCien;
	private Float nota;
	
}
