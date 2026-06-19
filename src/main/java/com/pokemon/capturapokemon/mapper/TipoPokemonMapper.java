package com.pokemon.capturapokemon.mapper;

import com.pokemon.capturapokemon.dto.TipoPokemonDto;
import com.pokemon.capturapokemon.model.TipoPokemon;
import org.springframework.stereotype.Component;

@Component
public class TipoPokemonMapper {

    public TipoPokemonDto toDto(TipoPokemon entity) {
        if (entity == null) {
            return null;
        }
        return new TipoPokemonDto(entity.getUuid(), entity.getDescripcion());
    }

    public TipoPokemon toEntity(TipoPokemonDto dto) {
        if (dto == null) {
            return null;
        }
        return TipoPokemon.builder()
                .uuid(dto.uuid())
                .descripcion(dto.descripcion())
                .build();
    }
}
