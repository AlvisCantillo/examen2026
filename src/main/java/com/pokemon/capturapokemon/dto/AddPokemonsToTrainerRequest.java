package com.pokemon.capturapokemon.dto;

import java.util.List;

public record AddPokemonsToTrainerRequest(
    List<String> pokemonUuids
) {}
