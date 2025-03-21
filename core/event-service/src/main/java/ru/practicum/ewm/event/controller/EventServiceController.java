package ru.practicum.ewm.event.controller;

import ru.practicum.ewm.client.EventClient;
import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class EventServiceController implements EventClient {

    private final EventService service;

    @Override
    public EventDto getEventById(@PathVariable Long eventId) {
        return service.publicGetEvent(eventId);
    }

    @Override
    public EventDto updateConfirmRequests(@PathVariable Long eventId, @RequestBody EventDto event) {
        return service.updateConfirmRequests(event);
    }
}
