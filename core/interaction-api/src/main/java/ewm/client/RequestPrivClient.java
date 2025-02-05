package ewm.client;

import ewm.dto.request.RequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "request-priv-client")
public interface RequestPrivClient {
    @GetMapping
    public List<RequestDto> getRequests(@PathVariable Long userId) ;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RequestDto createRequest(@PathVariable Long userId,
                                    @RequestParam Long eventId) ;

    @PatchMapping("/{requestId}/cancel")
    RequestDto cancelRequest(@PathVariable Long userId,
                             @PathVariable Long requestId);
}
