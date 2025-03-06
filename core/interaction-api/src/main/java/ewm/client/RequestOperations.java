package ewm.client;

import ewm.dto.request.RequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RequestOperations {
    @GetMapping("request/event/{eventId}")
    List<RequestDto> getRequestsByEventId(@PathVariable Long eventId);

    @GetMapping("request")
    List<RequestDto> findAllById(@RequestBody List<Long> ids);

    @PatchMapping("request")
    RequestDto updateRequest(@RequestBody RequestDto requestDto);

    @PatchMapping("request/all")
    List<RequestDto> updateAllRequest(@RequestBody List<RequestDto> requestDtoList);
}
