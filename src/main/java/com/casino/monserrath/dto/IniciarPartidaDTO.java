package com.casino.monserrath.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class IniciarPartidaDTO {
    @NotNull(message = "El id del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @Positive(message = "La apuesta debe ser mayor a cero")
    private Double apuesta;
}