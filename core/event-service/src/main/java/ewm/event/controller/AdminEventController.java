package ewm.event.controller;


import ewm.dto.event.AdminGetEventRequestDto;
import ewm.dto.event.EventDto;
import ewm.dto.event.UpdateEventDto;
import ewm.event.service.EventService;
import ewm.event.validate.EventValidate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("admin/events")
public class AdminEventController  {
	@Autowired
	private EventService eventService;

	@GetMapping
	public List<EventDto> adminGetEvents(AdminGetEventRequestDto requestParams) {
		log.info("Получить события, согласно устловиям -> {}", requestParams);
		return eventService.adminGetEvents(requestParams);
	}

	@PatchMapping("/{eventId}")
	public EventDto adminChangeEvent(@PathVariable Long eventId,
									 @RequestBody @Valid UpdateEventDto eventDto) {
		log.info("Изменить событие eventId = {}, поля -> {}", eventId, eventDto);
		EventValidate.updateEventDateValidate(eventDto, log);
		EventValidate.textLengthValidate(eventDto, log);
		return eventService.adminChangeEvent(eventId, eventDto);
	}
}
