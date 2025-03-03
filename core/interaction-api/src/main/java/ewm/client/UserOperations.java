package ewm.client;

import ewm.dto.user.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserOperations {
    @GetMapping("/{userId}")
    UserDto getUserById(@PathVariable Long userId);
}
