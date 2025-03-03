package com.coleccion.videojuegos.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.coleccion.videojuegos.entity.Enums.Genero;
import com.coleccion.videojuegos.entity.Enums.Plataforma;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="VIDEOJUEGO")
public class Videojuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name="PRECIO")
    private Float precio;

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

    @OneToMany(mappedBy = "videojuego", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Progreso> progreso = new ArrayList<>();

    @OneToMany(mappedBy = "videojuego", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Soporte> soporte = new ArrayList<>();

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    public void addProgreso(Progreso elementoProgreso) {
        this.progreso.add(elementoProgreso);
        elementoProgreso.setVideojuego(this);
    }

    public void addSoporte(Soporte elementoSoporte) {
        this.soporte.add(elementoSoporte);
        elementoSoporte.setVideojuego(this);
    }
}
