package com.pokemon.capturapokemon.mapper;

import com.pokemon.capturapokemon.dto.PuebloDto;
import com.pokemon.capturapokemon.model.Pueblo;
import org.springframework.stereotype.Component;

@Component
public class PuebloMapper {

    public PuebloDto toDto(Pueblo entity) {
        if (entity == null) {
            return null;
        }
        return new PuebloDto(entity.getUuid(), entity.getNombre());
    }

    public Pueblo toEntity(PuebloDto dto) {
        if (dto == null) {
            return null;
        }
        return Pueblo.builder()
                .uuid(dto.uuid())
                .nombre(dto.nombre())
                .build();
    }
}
