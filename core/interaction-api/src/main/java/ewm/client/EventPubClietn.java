//package ewm.client;
//
//import ewm.dto.event.EventDto;
//import ewm.dto.event.PublicGetEventRequestDto;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@FeignClient(name = "event-service", contextId = "EventPubClient", path="events")
//public interface EventPubClietn {
//
//    @GetMapping("/{id}")
//    EventDto publicGetEvent(@PathVariable Long id);
//
//    @GetMapping("/category/{id}")
//    List<EventDto> findByCategoryId(@PathVariable Long id);
//}
