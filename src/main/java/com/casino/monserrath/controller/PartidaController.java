package com.casino.monserrath.controller;

import com.casino.monserrath.dto.IniciarPartidaDTO;
import com.casino.monserrath.model.Partida;
import com.casino.monserrath.model.Tiro;
import com.casino.monserrath.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Partida> iniciarPartida(@Valid @RequestBody IniciarPartidaDTO dto) {
        return ResponseEntity.ok(partidaService.iniciarPartida(dto.getJugadorId(), dto.getApuesta()));
    }

    @PostMapping("/{id}/tirar")
    public ResponseEntity<Tiro> realizarTiro(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.realizarTiro(id));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Partida> finalizarPartidaManual(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.finalizarPartidaManual(id));
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<Partida>> obtenerHistorial(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(partidaService.obtenerHistorialPartida(jugadorId));
    }
}