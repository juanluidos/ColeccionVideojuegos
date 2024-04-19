//Esta es la parte de """"gaming"""" sobre el progreso de cada partida que haya echado A CAda juego
package com.coleccion.videojuegos.entity;

import com.coleccion.videojuegos.entity.Enums.Avance;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
@Entity
@Table(name="PROGRESO")
public class Progreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
	@Column(name = "ANYO_JUGADO")
    private Integer anyoJugado;
	@Enumerated(EnumType.STRING)
	@Column(name="AVANCE")
	private Avance avance;
	@Column(name = "HORAS_JUGADAS")
    private Float horasJugadas;
	@Column(name = "COMPLETADO100")
    private Boolean completadoCien;
	@Column(name = "NOTA")
    private Float nota;
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

	public Integer getAnyoJugado() {
		return this.anyoJugado;
	}

	public void setAnyoJugado(Integer anyoJugado) {
		this.anyoJugado = anyoJugado;
	}

	public Avance getAvance() {
		return this.avance;
	}

	public void setAvance(Avance avance) {
		this.avance = avance;
	}

	public Float getHorasJugadas() {
		return this.horasJugadas;
	}

	public void setHorasJugadas(Float horasJugadas) {
		this.horasJugadas = horasJugadas;
	}

	public Float getNota() {
		return this.nota;
	}

	public void setNota(Float nota) {
		this.nota = nota;
	}


	public Boolean isCompletadoCien() {
		return this.completadoCien;
	}

	public Boolean getCompletadoCien() {
		return this.completadoCien;
	}

	public void setCompletadoCien(boolean completadoCien) {
		this.completadoCien = completadoCien;
	}

	public Videojuego getVideojuego() {
		return this.videojuego;
	}

	public void setVideojuego(Videojuego videojuego) {
		this.videojuego = videojuego;
	}


}
