package com.pokemon.capturapokemon.repository;

import com.pokemon.capturapokemon.model.TipoPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoPokemonRepository extends JpaRepository<TipoPokemon, Integer> {
    Optional<TipoPokemon> findByUuid(String uuid);
}
