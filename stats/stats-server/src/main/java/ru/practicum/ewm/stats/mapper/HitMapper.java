package ru.practicum.ewm.stats.mapper;

import ru.practicum.ewm.dto.EndpointHitResponseDto;
import ru.practicum.ewm.stats.model.Hit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HitMapper {
    HitMapper INSTANCE = Mappers.getMapper(HitMapper.class);

    EndpointHitResponseDto hitToEndpointHitResponseDto(Hit hit);
}
