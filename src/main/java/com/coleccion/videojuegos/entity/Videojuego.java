package com.coleccion.videojuegos.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.coleccion.videojuegos.entity.Enums.Genero;
import com.coleccion.videojuegos.entity.Enums.Plataforma;

import jakarta.persistence.*;

public class Videojuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
	@Column(name = "NOMBRE")
    private String anyoJugado;
	@Column(name="PRECIO")
	private float precio;
	@Column(name="FECHA_LANZAMIENTO")
	private Date fechaLanzamiento;
	@Column(name="FECHA_COMPRA")
	private Date fechaCompra;
	@Enumerated(EnumType.STRING)
	@Column(name="PLATAFORMA")
	private Plataforma plataforma;
	@Enumerated(EnumType.STRING)
	@Column(name="GENERO")
	private Genero genero;
	@OneToMany(mappedBy = "videojuego", cascade = CascadeType.ALL)
	@Column(name="PROGRESO")
	private List<Progreso> progreso = new ArrayList<>();
	@OneToMany(mappedBy = "videojuego", cascade = CascadeType.ALL)
	@Column(name="SOPORTE")
	private List<Soporte> soporte = new ArrayList<>();

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnyoJugado() {
		return this.anyoJugado;
	}

	public void setAnyoJugado(String anyoJugado) {
		this.anyoJugado = anyoJugado;
	}

	public float getPrecio() {
		return this.precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Date getFechaLanzamiento() {
		return this.fechaLanzamiento;
	}

	public void setFechaLanzamiento(Date fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

	public Date getFechaCompra() {
		return this.fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Plataforma getPlataforma() {
		return this.plataforma;
	}

	public void setPlataforma(Plataforma plataforma) {
		this.plataforma = plataforma;
	}

	public Genero getGenero() {
		return this.genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public List<Progreso> getProgreso() {
		return this.progreso;
	}

	public void setProgreso(List<Progreso> progreso) {
		this.progreso = progreso;
	}

	public List<Soporte> getSoporte() {
		return this.soporte;
	}

	public void setSoporte(List<Soporte> soporte) {
		this.soporte = soporte;
	}
}
