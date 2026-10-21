package com.casino.monserrath.repository;

import com.casino.monserrath.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByJugadorId(Long jugadorId);
}