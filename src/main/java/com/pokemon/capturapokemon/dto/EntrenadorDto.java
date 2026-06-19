package com.pokemon.capturapokemon.dto;

import java.time.LocalDate;
import java.util.List;

public record EntrenadorDto(
    Integer id,
    String nombreCompleto,
    LocalDate fechaNacimiento,
    LocalDate fechaVinculacion,
    String uuid,
    String pueblo,
    List<String> pokemonsCapturados
) {}
