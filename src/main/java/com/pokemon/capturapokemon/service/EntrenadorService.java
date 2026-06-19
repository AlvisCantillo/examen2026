package com.pokemon.capturapokemon.service;

import com.pokemon.capturapokemon.dto.EntrenadorDto;
import com.pokemon.capturapokemon.dto.PokemonDto;
import com.pokemon.capturapokemon.model.Entrenador;
import com.pokemon.capturapokemon.model.Pokemon;
import com.pokemon.capturapokemon.model.Pueblo;
import com.pokemon.capturapokemon.repository.EntrenadorRepository;
import com.pokemon.capturapokemon.repository.PokemonRepository;
import com.pokemon.capturapokemon.repository.PuebloRepository;
import com.pokemon.capturapokemon.mapper.EntrenadorMapper;
import com.pokemon.capturapokemon.mapper.PokemonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Autowired
    private PuebloRepository puebloRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private EntrenadorMapper entrenadorMapper;

    @Autowired
    private PokemonMapper pokemonMapper;

    @Transactional(readOnly = true)
    public List<EntrenadorDto> getAllEntrenadores() {
        return entrenadorRepository.findAll().stream()
                .map(entrenadorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<String> getUuidByEmail(String email) {
        return entrenadorRepository.findByEmail(email)
                .map(Entrenador::getUuid);
    }

    @Transactional(readOnly = true)
    public List<PokemonDto> getPokemonsByEntrenadorUuid(String uuid) {
        Entrenador entrenador = entrenadorRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado: " + uuid));
        return entrenador.getPokemons().stream()
                .map(pokemonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PokemonDto> addPokemonsToEntrenador(String entrenadorUuid, List<String> pokemonUuids) {
        Entrenador entrenador = entrenadorRepository.findByUuid(entrenadorUuid)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado: " + entrenadorUuid));

        List<Pokemon> pokemons = new ArrayList<>();
        for (String pokeUuid : pokemonUuids) {
            Pokemon p = pokemonRepository.findByUuid(pokeUuid)
                    .orElseThrow(() -> new RuntimeException("Pokemon no encontrado: " + pokeUuid));
            pokemons.add(p);
        }

        if (entrenador.getPokemons() == null) {
            entrenador.setPokemons(new ArrayList<>());
        }
        for (Pokemon p : pokemons) {
            if (!entrenador.getPokemons().contains(p)) {
                entrenador.getPokemons().add(p);
            }
        }

        entrenadorRepository.save(entrenador);
        return entrenador.getPokemons().stream()
                .map(pokemonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Entrenador saveEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    @Transactional
    public Pueblo savePueblo(Pueblo pueblo) {
        return puebloRepository.save(pueblo);
    }
}
