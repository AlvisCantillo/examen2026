package com.pokemon.capturapokemon.mapper;

import com.pokemon.capturapokemon.dto.EntrenadorDto;
import com.pokemon.capturapokemon.model.Entrenador;
import com.pokemon.capturapokemon.model.Pokemon;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntrenadorMapper {

    public EntrenadorDto toDto(Entrenador entity) {
        if (entity == null) {
            return null;
        }
        
        String puebloNombre = entity.getPueblo() != null ? entity.getPueblo().getNombre() : null;
        
        List<String> pokemonsCapturados = new ArrayList<>();
        if (entity.getPokemons() != null) {
            pokemonsCapturados = entity.getPokemons().stream()
                    .map(Pokemon::getNombre)
                    .collect(Collectors.toList());
        }

        return new EntrenadorDto(
                entity.getId(),
                entity.getNombre() + " " + entity.getApellido(),
                entity.getFechaNacimiento(),
                entity.getFechaVinculacion(),
                entity.getUuid(),
                puebloNombre,
                pokemonsCapturados
        );
    }
}
