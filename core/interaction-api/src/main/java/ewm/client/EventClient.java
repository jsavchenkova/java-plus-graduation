package ewm.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "event-service", path="/event")
public interface EventClient extends EventOperations {


}
