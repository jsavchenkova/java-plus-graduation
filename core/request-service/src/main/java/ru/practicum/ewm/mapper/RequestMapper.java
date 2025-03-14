package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.request.RequestDto;
import ru.practicum.ewm.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RequestMapper {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    @Mapping(source = "eventId", target = "event")
    @Mapping(source = "requesterId", target = "requester")
    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    RequestDto mapToRequestDto(Request request);

    List<RequestDto> mapListRequests(List<Request> requests);

    Request mapDtoToRequest(RequestDto requestDto);

    List<Request> mapDtoToRequestList(List<RequestDto> requestDtoList);

}
