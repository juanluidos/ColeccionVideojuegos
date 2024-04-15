package com.coleccion.videojuegos.entity;

import com.coleccion.videojuegos.entity.Enums.Distribucion;
import com.coleccion.videojuegos.entity.Enums.Edicion;
import com.coleccion.videojuegos.entity.Enums.Estado;
import com.coleccion.videojuegos.entity.Enums.Region;
import com.coleccion.videojuegos.entity.Enums.Tipo;

import jakarta.persistence.*;

public class Soporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO")
	private Tipo tipo;
	@Enumerated(EnumType.STRING)
	@Column(name="ESTADO")
	private Estado estado;
	@Enumerated(EnumType.STRING)
	@Column(name="EDICION")
	private Edicion edicion;
	@Enumerated(EnumType.STRING)
	@Column(name="DISTRIBUCION")
	private Distribucion distribucion;
	@Column(name="PRECINTADO")
	private boolean precintado;
	@Column(name="REGION")
	@Enumerated(EnumType.STRING)
	private Region region;
	@Column(name = "ANYO_SALIDA_DISTRIBUCION")
    private int anyoSalidaDist;


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Edicion getEdicion() {
		return this.edicion;
	}

	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}

	public Distribucion getDistribucion() {
		return this.distribucion;
	}

	public void setDistribucion(Distribucion distribucion) {
		this.distribucion = distribucion;
	}

	public boolean isPrecintado() {
		return this.precintado;
	}

	public boolean getPrecintado() {
		return this.precintado;
	}

	public void setPrecintado(boolean precintado) {
		this.precintado = precintado;
	}

	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public int getAnyoSalidaDist() {
		return this.anyoSalidaDist;
	}

	public void setAnyoSalidaDist(int anyoSalidaDist) {
		this.anyoSalidaDist = anyoSalidaDist;
	}

}
