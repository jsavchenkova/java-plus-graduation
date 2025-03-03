package ewm.request.controller.priv;

import ewm.client.RequestClient;
import ewm.dto.request.RequestDto;
import ewm.request.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("requests")
public class RequsetController {

    private RequestService service;

    @GetMapping("/events/{eventId}")
    public List<RequestDto> findAllByEventId(@PathVariable Long eventId){
        return service.findAllByEventId(eventId);
    }

}
