package com.casino.monserrath.service;

import com.casino.monserrath.exception.RecursoNoEncontradoException;
import com.casino.monserrath.exception.ReglaNegocioException;
import com.casino.monserrath.model.*;
import com.casino.monserrath.repository.JugadorRepository;
import com.casino.monserrath.repository.PartidaRepository;
import com.casino.monserrath.repository.TiroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final TiroRepository tiroRepository;
    private final Random random = new Random();

    public PartidaService(PartidaRepository partidaRepository, JugadorRepository jugadorRepository, TiroRepository tiroRepository) {
        this.partidaRepository = partidaRepository;
        this.jugadorRepository = jugadorRepository;
        this.tiroRepository = tiroRepository;
    }

    public Partida iniciarPartida(Long jugadorId, Double apuesta) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El jugador no existe"));

        if (!jugador.getActivo()) {
            throw new ReglaNegocioException("El jugador no está activo");
        }

        if (jugador.getSaldo() < apuesta) {
            throw new ReglaNegocioException("Saldo insuficiente para realizar la apuesta");
        }

        jugador.setSaldo(jugador.getSaldo() - apuesta);
        jugadorRepository.save(jugador);

        Partida partida = new Partida();
        partida.setJugador(jugador);
        partida.setApuesta(apuesta);
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);

        return partidaRepository.save(partida);
    }

    public Tiro realizarTiro(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("La partida no existe"));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new ReglaNegocioException("La partida ya está finalizada o no está activa");
        }

        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;

        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);

        Jugador jugador = partida.getJugador();

        if (suma == 7 || suma == 11) {
            tiro.setEsGanador(true);
            partida.setEstado(EstadoPartida.FINALIZADA);
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepository.save(jugador);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            tiro.setEsGanador(false);
            partida.setEstado(EstadoPartida.FINALIZADA);
        } else {
            tiro.setEsGanador(false);
        }

        partidaRepository.save(partida);
        return tiroRepository.save(tiro);
    }

    public Partida finalizarPartidaManual(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("La partida no existe"));

        partida.setEstado(EstadoPartida.FINALIZADA);
        return partidaRepository.save(partida);
    }

    public List<Partida> obtenerHistorialPartida(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new RecursoNoEncontradoException("El jugador no existe");
        }
        return partidaRepository.findByJugadorId(jugadorId);
    }
}