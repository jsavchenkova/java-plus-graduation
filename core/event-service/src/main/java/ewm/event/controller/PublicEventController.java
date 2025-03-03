package ewm.event.controller;


import ewm.dto.event.EventDto;
import ewm.dto.event.PublicGetEventRequestDto;
import ewm.event.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("events")
public class PublicEventController {
	@Autowired
	private EventService eventService;

@GetMapping
	public List<EventDto> publicGetEvents(@RequestParam  PublicGetEventRequestDto requestParams) {
		log.info("Получить события, согласно устловиям -> {}", requestParams);
		return eventService.publicGetEvents(requestParams);
	}

	@GetMapping("/{id}")
	public EventDto publicGetEvent(@PathVariable Long id) {
		return eventService.publicGetEvent(id);
	}


	public List<EventDto> findByCategoryId(Long id) {
		return eventService.getByCategoryId(id);
	}
}
