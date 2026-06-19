package com.pokemon.capturapokemon.dto;

import java.time.LocalDate;

public record PokemonDto(
    String uuid,
    String nombre,
    String descripcion,
    TipoPokemonDto tipoPokemon,
    LocalDate fechaDescubrimiento,
    Integer generacion
) {}
