package ewm.event.controller;

import ewm.dto.event.*;

import ewm.event.service.EventService;
import ewm.event.validate.EventValidate;
import ewm.dto.request.RequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "users/{userId}/events")
@RequiredArgsConstructor
public class UserEventController  {
	@Autowired
	private EventService service;

	@GetMapping
	public List<EventDto> getEvents(@PathVariable Long userId,
									@RequestParam(name = "from", defaultValue = "0") Integer from,
									@RequestParam(name = "size", defaultValue = "10") Integer size) {
		return service.getEvents(userId, from, size);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public EventDto createEvent(@PathVariable Long userId,
								@Valid @RequestBody CreateEventDto event) {
		EventValidate.eventDateValidate(event, log);
		return service.createEvent(userId, event);
	}

	@GetMapping("/{id}")
	public EventDto getEvent(@PathVariable Long userId,
							 @PathVariable Long id,
							 HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		String uri = request.getRequestURI();
		return service.getEventById(userId, id, ip, uri);
	}

	@PatchMapping("/{eventId}")
	public EventDto updateEvent(@PathVariable Long userId,
								@PathVariable Long eventId,
								@Valid @RequestBody UpdateEventDto event) {
//		EventValidate.updateEventDateValidate(event, log);
//		EventValidate.textLengthValidate(event, log);
		return service.updateEvent(userId, event, eventId);
	}

	@GetMapping("/{eventId}/requests")
	public List<RequestDto> getEventRequests(@PathVariable Long userId,
											 @PathVariable Long eventId) {
		return service.getEventRequests(userId, eventId);
	}

	@PatchMapping("/{eventId}/requests")
	public EventRequestStatusUpdateResult changeStatusEventRequests(@PathVariable Long userId,
																	@PathVariable Long eventId,
																	@RequestBody
																	@Valid EventRequestStatusUpdateRequest request) {
		return service.changeStatusEventRequests(userId, eventId, request);
	}
}
