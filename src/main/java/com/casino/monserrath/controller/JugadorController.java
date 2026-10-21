package com.casino.monserrath.controller;

import com.casino.monserrath.dto.RecargarSaldoDTO;
import com.casino.monserrath.model.Jugador;
import com.casino.monserrath.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@RequestBody Jugador jugador) {
        return ResponseEntity.ok(jugadorService.crearJugador(jugador));
    }

    @PostMapping("/{id}/recargar")
    public ResponseEntity<Jugador> recargarSaldo(@PathVariable Long id, @Valid @RequestBody RecargarSaldoDTO dto) {
        return ResponseEntity.ok(jugadorService.recargarSaldo(id, dto.getMonto()));
    }
}
