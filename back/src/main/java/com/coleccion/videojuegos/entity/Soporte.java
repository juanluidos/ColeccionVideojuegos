//Esta es la parte de coleccionismo
package com.coleccion.videojuegos.entity;

import com.coleccion.videojuegos.entity.Enums.Distribucion;
import com.coleccion.videojuegos.entity.Enums.Edicion;
import com.coleccion.videojuegos.entity.Enums.Estado;
import com.coleccion.videojuegos.entity.Enums.Region;
import com.coleccion.videojuegos.entity.Enums.Tienda;
import com.coleccion.videojuegos.entity.Enums.Tipo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.websocket.OnClose;

@Entity
@Table(name="SOPORTE")
public class Soporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
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
	private Boolean precintado;
	@Column(name="REGION")
	@Enumerated(EnumType.STRING)
	private Region region;
	@Column(name = "ANYO_SALIDA_DISTRIBUCION")
    private Integer anyoSalidaDist;
	@Column(name = "TIENDA")
	@Enumerated(EnumType.STRING)
    private Tienda tienda;
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_VIDEOJUEGO")
    private Videojuego videojuego;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public Boolean isPrecintado() {
		return this.precintado;
	}

	public Boolean getPrecintado() {
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

	public Integer getAnyoSalidaDist() {
		return this.anyoSalidaDist;
	}

	public void setAnyoSalidaDist(Integer anyoSalidaDist) {
		this.anyoSalidaDist = anyoSalidaDist;
	}

	public Videojuego getVideojuego() {
		return this.videojuego;
	}

	public void setVideojuego(Videojuego videojuego) {
		this.videojuego = videojuego;
	}

	public Tienda getTienda() {
		return this.tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}
	
	@Override
	public String toString(){
		return this.tipo + " + " +this.estado;
	}
}
