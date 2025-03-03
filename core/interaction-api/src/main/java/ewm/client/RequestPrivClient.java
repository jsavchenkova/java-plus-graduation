package ewm.client;

import ewm.dto.request.RequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "request-service", contextId = "RequestPrivClient", path = "users/{userId}/requests")
public interface RequestPrivClient {
    @GetMapping
    List<RequestDto> getRequests(@PathVariable Long userId);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    RequestDto createRequest(@PathVariable Long userId,
                             @RequestParam Long eventId,
                             HttpServletRequest request);

    @PatchMapping("/{requestId}/cancel")
    RequestDto cancelRequest(@PathVariable Long userId,
                             @PathVariable Long requestId);
}
