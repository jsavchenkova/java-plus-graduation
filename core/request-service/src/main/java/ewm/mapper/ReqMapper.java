package ewm.mapper;

import ewm.dto.request.RequestDto;
import ewm.model.Request;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReqMapper {
    public static RequestDto mapToRequestDto(Request request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        RequestDto dto = RequestDto.builder()
                .created(request.getCreated().format(formatter))
                .event(request.getEventId())
                .id(request.getId())
                .requester(request.getRequesterId())
                .status(request.getStatus())
                .build();
        return dto;
    }

    public static List<RequestDto> mapListRequests(List<Request> requests) {
        List<RequestDto> list = new ArrayList<>(requests.size());
        for (Request r : requests) {
            list.add(mapToRequestDto(r));
        }
        return list;
    }

    public static Request mapDtoToRequest(RequestDto requestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Request request = Request.builder()
                .created(LocalDateTime.parse(requestDto.getCreated(), formatter))
                .eventId(requestDto.getEvent())
                .requesterId(requestDto.getRequester())
                .status(requestDto.getStatus())
                .build();
        request.setId(requestDto.getId());
        return request;
    }

    public static List<Request> mapDtoToRequestList(List<RequestDto> requestDtoList) {
        List<Request> list = new ArrayList<>(requestDtoList.size());
        for (RequestDto r : requestDtoList) {
            list.add(mapDtoToRequest(r));
        }
        return list;
    }
}
