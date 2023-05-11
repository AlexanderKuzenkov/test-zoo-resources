package com.kuzenkov.zooresources.mapper;

import com.kuzenkov.zooresources.api.model.Animal;
import com.kuzenkov.zooresources.api.model.AnimalCreate;
import com.kuzenkov.zooresources.config.IgnoreUnmappedMapperConfig;
import com.kuzenkov.zooresources.entity.AnimalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface AnimalMapper {
    AnimalMapper INSTANCE = Mappers.getMapper(AnimalMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "name", source = "entity.name"),
            @Mapping(target = "species", source = "entity.species"),
            @Mapping(target = "isCarnivorous", source = "entity.isCarnivorous")
    })
    Animal toApiAnimal(AnimalEntity entity);

    @Mappings({
            @Mapping(target = "name", source = "entity.name"),
            @Mapping(target = "species", source = "entity.species"),
            @Mapping(target = "isCarnivorous", source = "entity.isCarnivorous")
    })
    AnimalEntity createAnimal(AnimalCreate entity);
}
