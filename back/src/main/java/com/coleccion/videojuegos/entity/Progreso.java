//Esta es la parte de """"gaming"""" sobre el progreso de cada partida que haya echado A CAda juego
package com.coleccion.videojuegos.entity;

import com.coleccion.videojuegos.entity.Enums.Avance;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
