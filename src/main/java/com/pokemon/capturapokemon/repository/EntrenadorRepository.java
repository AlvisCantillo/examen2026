package com.pokemon.capturapokemon.repository;

import com.pokemon.capturapokemon.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Integer> {
    Optional<Entrenador> findByUuid(String uuid);
    Optional<Entrenador> findByEmail(String email);
}
