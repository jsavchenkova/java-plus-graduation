package ru.practicum.ewm.contoroller;

import ru.practicum.ewm.client.UserClient;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserServiceController implements UserClient {
    private final UserService service;

    @Override
    public UserDto getUserById(Long userId) {
        return service.getUserById(userId);
    }
}
