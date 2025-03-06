package ewm.event.controller;

import ewm.client.EventClient;
import ewm.dto.event.EventDto;
import ewm.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class EventServiceController implements EventClient {

    private final EventService service;

    @Override
    @GetMapping("/event/{eventId}")
    public EventDto getEventById(Long eventId) {
        return service.publicGetEvent(eventId);
    }

    @Override
    @PostMapping("/event/{eventId}")
    public EventDto updateConfirmRequests(EventDto event) {
        return service.updateConfirmRequests(event);
    }
}
