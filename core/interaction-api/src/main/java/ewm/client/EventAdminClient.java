//package ewm.client;
//
//import ewm.dto.event.AdminGetEventRequestDto;
//import ewm.dto.event.EventDto;
//import ewm.dto.event.UpdateEventDto;
//import jakarta.validation.Valid;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@FeignClient(name = "event-service", contextId = "EventAdminClient", path="admin/events")
//public interface EventAdminClient {
//
//    @GetMapping
//    List<EventDto> adminGetEvents(AdminGetEventRequestDto requestParams);
//
//    @PatchMapping("/{eventId}")
//    EventDto adminChangeEvent(@PathVariable Long eventId,
//                              @RequestBody @Valid UpdateEventDto eventDto);
//}
