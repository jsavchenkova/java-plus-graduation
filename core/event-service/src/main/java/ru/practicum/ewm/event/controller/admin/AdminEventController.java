package ru.practicum.ewm.event.controller.admin;

import ru.practicum.ewm.dto.event.AdminGetEventRequestDto;
import ru.practicum.ewm.dto.event.UpdateEventDto;
import ru.practicum.ewm.dto.event.UpdatedEventDto;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.validate.EventValidate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("admin/events")
public class AdminEventController {
	private final EventService eventService;

	@GetMapping
	public List<UpdatedEventDto> adminGetEvents(AdminGetEventRequestDto requestParams) {
		log.info("Получить события, согласно устловиям -> {}", requestParams);
		return eventService.adminGetEvents(requestParams);
	}

	@PatchMapping("/{eventId}")
	public UpdatedEventDto adminChangeEvent(@PathVariable Long eventId,
											@RequestBody @Valid UpdateEventDto eventDto) {
		log.info("Изменить событие eventId = {}, поля -> {}", eventId, eventDto);
		EventValidate.updateEventDateValidate(eventDto, log);
		EventValidate.textLengthValidate(eventDto, log);
		return eventService.adminChangeEvent(eventId, eventDto);
	}
}
