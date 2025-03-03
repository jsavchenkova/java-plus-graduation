package ewm.client;

import ewm.dto.event.EventDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface EventOperations {
    @GetMapping("/{eventId}")
    EventDto getEventById(@PathVariable Long eventId);


}
