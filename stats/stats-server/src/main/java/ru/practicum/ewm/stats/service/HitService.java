package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.dto.EndpointHitDTO;
import ru.practicum.ewm.dto.EndpointHitResponseDto;
import ru.practicum.ewm.dto.StatsRequestDTO;
import ru.practicum.ewm.dto.ViewStatsDTO;

import java.util.List;

public interface HitService {
    EndpointHitResponseDto create(EndpointHitDTO endpointHitDTO);

    List<ViewStatsDTO> getAll(StatsRequestDTO statsRequestDTO);
}
