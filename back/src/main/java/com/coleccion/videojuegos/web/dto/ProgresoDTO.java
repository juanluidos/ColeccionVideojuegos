package com.coleccion.videojuegos.web.dto;

import com.coleccion.videojuegos.entity.Enums.Avance;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProgresoDTO {
    private Integer anyoJugado;
    private Avance avance;
    
    @Positive(message = "Las horas jugadas deben ser positivas")
    private Float horasJugadas;

    private Boolean completadoCien;
    private Float nota;
}
