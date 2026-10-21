package com.casino.monserrath.service;
import com.casino.monserrath.exception.RecursoNoEncontradoException;
import com.casino.monserrath.model.Jugador;
import com.casino.monserrath.repository.JugadorRepository;
import org.springframework.stereotype.Service;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public Jugador crearJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public Jugador recargarSaldo(Long jugadorId, Double monto) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El jugador no existe"));
        jugador.setSaldo(jugador.getSaldo() + monto);
        return jugadorRepository.save(jugador);
    }
}
