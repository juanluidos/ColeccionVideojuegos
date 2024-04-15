package com.coleccion.videojuegos.entity;

import com.coleccion.videojuegos.entity.Enums.Avance;

import jakarta.persistence.*;

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
    private int horasJugadas;
	@Column(name = "NOTA")
    private int nota;


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

	public int getHorasJugadas() {
		return this.horasJugadas;
	}

	public void setHorasJugadas(int horasJugadas) {
		this.horasJugadas = horasJugadas;
	}

	public int getNota() {
		return this.nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

}
