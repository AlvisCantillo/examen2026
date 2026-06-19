package com.pokemon.capturapokemon.dto;

import java.time.LocalDate;

public record CreatePokemonRequest(
    String nombre,
    String descripcion,
    String tipoUuid,
    LocalDate fechaDescubrimiento,
    Integer generacion
) {}
