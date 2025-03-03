package com.coleccion.videojuegos.entity;

import com.coleccion.videojuegos.entity.Enums.Avance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
