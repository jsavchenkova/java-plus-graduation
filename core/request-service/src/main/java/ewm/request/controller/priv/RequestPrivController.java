package ewm.request.controller.priv;

import ewm.client.RequestPrivClient;
import ewm.dto.request.RequestDto;
import ewm.request.service.RequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("users/{userId}/requests")
public class RequestPrivController {
	@Autowired
	private  RequestService requestService;


	@GetMapping
	public List<RequestDto> getRequests(@PathVariable Long userId) {
		log.info("Получить запросы по userId --> {}", userId);
		return requestService.getRequests(userId);
	}

//	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public RequestDto createRequest(@PathVariable Long userId,
									@RequestParam Long eventId) {
		log.info("Создать запрос userId --> {}, eventId --> {}", userId, eventId);
		return requestService.createRequest(userId, eventId);
	}

	@PatchMapping("/{requestId}/cancel")
	public RequestDto cancelRequest(@PathVariable Long userId,
									@PathVariable Long requestId) {
		log.info("Отменить запрос по userId --> {}, requestId --> {}", userId, requestId);
		return requestService.cancelRequest(userId, requestId);
	}


}
