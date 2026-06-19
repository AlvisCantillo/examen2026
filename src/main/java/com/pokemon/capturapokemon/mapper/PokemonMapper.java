package com.pokemon.capturapokemon.mapper;

import com.pokemon.capturapokemon.dto.PokemonDto;
import com.pokemon.capturapokemon.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PokemonMapper {

    @Autowired
    private TipoPokemonMapper tipoPokemonMapper;

    public PokemonDto toDto(Pokemon entity) {
        if (entity == null) {
            return null;
        }
        return new PokemonDto(
                entity.getUuid(),
                entity.getNombre(),
                entity.getDescripcion(),
                tipoPokemonMapper.toDto(entity.getTipoPokemon()),
                entity.getFechaDescubrimiento(),
                entity.getGeneracion()
        );
    }

    public Pokemon toEntity(PokemonDto dto) {
        if (dto == null) {
            return null;
        }
        return Pokemon.builder()
                .uuid(dto.uuid())
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .tipoPokemon(tipoPokemonMapper.toEntity(dto.tipoPokemon()))
                .fechaDescubrimiento(dto.fechaDescubrimiento())
                .generacion(dto.generacion())
                .build();
    }
}
