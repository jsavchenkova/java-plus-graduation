package ewm.client;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ewm.dto.event.EventDto;
import ewm.dto.event.PublicGetEventRequestDto;

import java.util.List;

@FeignClient(name = "event-pub-client")
public interface EventPubClietn {

    @GetMapping
    public List<EventDto> publicGetEvents(HttpServletRequest request, PublicGetEventRequestDto requestParams);

    @GetMapping("/{id}")
    EventDto publicGetEvent(@PathVariable Long id,
                            HttpServletRequest request) ;
}
