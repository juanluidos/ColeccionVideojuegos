//Esta es la parte de """"gaming"""" sobre el progreso de cada partida que haya echado A CAda juego
package com.coleccion.videojuegos.entity;

import com.coleccion.videojuegos.entity.Enums.Avance;

import jakarta.persistence.*;
@Entity
@Table(name="PROGRESO")
public class Progreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
	@Column(name = "ANYO_JUGADO")
    private int anyoJugado;
	@Enumerated(EnumType.STRING)
	@Column(name="AVANCE")
	private Avance avance;
	@Column(name = "HORAS_JUGADAS")
    private float horasJugadas;
	@Column(name = "COMPLETADO100")
    private boolean completadoCien;
	@Column(name = "NOTA")
    private float nota;
    @ManyToOne
    @JoinColumn(name = "ID_VIDEOJUEGO")
    private Videojuego videojuego;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnyoJugado() {
		return this.anyoJugado;
	}

	public void setAnyoJugado(int anyoJugado) {
		this.anyoJugado = anyoJugado;
	}

	public Avance getAvance() {
		return this.avance;
	}

	public void setAvance(Avance avance) {
		this.avance = avance;
	}

	public float getHorasJugadas() {
		return this.horasJugadas;
	}

	public void setHorasJugadas(float horasJugadas) {
		this.horasJugadas = horasJugadas;
	}

	public float getNota() {
		return this.nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}


	public boolean isCompletadoCien() {
		return this.completadoCien;
	}

	public boolean getCompletadoCien() {
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
