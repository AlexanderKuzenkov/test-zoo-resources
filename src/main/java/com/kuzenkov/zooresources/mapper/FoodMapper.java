package com.kuzenkov.zooresources.mapper;

import com.kuzenkov.zooresources.api.model.Food;
import com.kuzenkov.zooresources.api.model.FoodCreate;
import com.kuzenkov.zooresources.config.IgnoreUnmappedMapperConfig;
import com.kuzenkov.zooresources.entity.FoodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface FoodMapper {
    FoodMapper INSTANCE = Mappers.getMapper(FoodMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "name", source = "entity.name"),
            @Mapping(target = "count", source = "entity.count"),
            @Mapping(target = "unit", source = "entity.unit"),
            @Mapping(target = "type", source = "entity.type")
    })
    Food toApiFood(FoodEntity entity);

    @Mappings({
            @Mapping(target = "name", source = "entity.name"),
            @Mapping(target = "count", source = "entity.count"),
            @Mapping(target = "unit", source = "entity.unit"),
            @Mapping(target = "type", source = "entity.type")
    })
    FoodEntity createFood(FoodCreate entity);
}
