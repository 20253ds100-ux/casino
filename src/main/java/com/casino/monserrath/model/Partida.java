package com.casino.monserrath.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private EstadoPartida estado;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    private Double apuesta;
}
