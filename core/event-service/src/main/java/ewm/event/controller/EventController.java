package ewm.event.controller;

import ewm.client.EventClient;
import ewm.client.EventOperations;
import ewm.dto.event.EventDto;
import ewm.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("event")
public class EventController implements EventOperations {
    @Autowired
    private EventService service;

    @Override
    @GetMapping("/{eventId}")
    public EventDto getEventById(@PathVariable Long eventId) {
        return service.publicGetEvent(eventId);
    }
}
