package ewm.client;

import ewm.dto.request.RequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "request-service", contextId = "RequestClient", path = "requests")
public interface RequestClient {

    @GetMapping("/events/{eventId}")
    public List<RequestDto> findAllByEventId(@PathVariable Long eventId);

}
