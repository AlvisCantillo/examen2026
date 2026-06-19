package com.pokemon.capturapokemon.service;

import com.pokemon.capturapokemon.dto.CreatePokemonRequest;
import com.pokemon.capturapokemon.dto.PokemonDto;
import com.pokemon.capturapokemon.model.Pokemon;
import com.pokemon.capturapokemon.model.TipoPokemon;
import com.pokemon.capturapokemon.repository.PokemonRepository;
import com.pokemon.capturapokemon.repository.TipoPokemonRepository;
import com.pokemon.capturapokemon.mapper.PokemonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private TipoPokemonRepository tipoPokemonRepository;

    @Autowired
    private PokemonMapper pokemonMapper;

    @Transactional(readOnly = true)
    public List<PokemonDto> getAllPokemons() {
        return pokemonRepository.findAll().stream()
                .map(pokemonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PokemonDto> getPokemonsByTipoUuid(String tipoUuid) {
        return pokemonRepository.findByTipoPokemonUuid(tipoUuid).stream()
                .map(pokemonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PokemonDto createPokemon(CreatePokemonRequest request) {
        TipoPokemon tipo = tipoPokemonRepository.findByUuid(request.tipoUuid())
                .orElseThrow(() -> new RuntimeException("TipoPokemon no encontrado: " + request.tipoUuid()));

        Pokemon pokemon = Pokemon.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .tipoPokemon(tipo)
                .fechaDescubrimiento(request.fechaDescubrimiento())
                .generacion(request.generacion())
                .uuid(UUID.randomUUID().toString())
                .build();

        return pokemonMapper.toDto(pokemonRepository.save(pokemon));
    }

    @Transactional
    public Pokemon savePokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @Transactional
    public TipoPokemon saveTipoPokemon(TipoPokemon tipoPokemon) {
        return tipoPokemonRepository.save(tipoPokemon);
    }
}
