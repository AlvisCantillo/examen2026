package com.pokemon.capturapokemon.service;

import com.pokemon.capturapokemon.dto.EntrenadorDto;
import com.pokemon.capturapokemon.model.Entrenador;
import com.pokemon.capturapokemon.model.Pueblo;
import com.pokemon.capturapokemon.repository.EntrenadorRepository;
import com.pokemon.capturapokemon.repository.PuebloRepository;
import com.pokemon.capturapokemon.mapper.EntrenadorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private EntrenadorMapper entrenadorMapper;

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

    @Transactional
    public Entrenador saveEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    @Transactional
    public Pueblo savePueblo(Pueblo pueblo) {
        return puebloRepository.save(pueblo);
    }
}
