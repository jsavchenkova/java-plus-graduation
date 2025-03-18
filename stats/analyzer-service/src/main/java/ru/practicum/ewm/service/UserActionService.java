package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.ActionType;
import ru.practicum.ewm.model.UserAction;
import ru.practicum.ewm.repository.UserActionRepository;
import ru.practicum.ewm.stats.avro.UserActionAvro;

@Service
@RequiredArgsConstructor
public class UserActionService {

    private final UserActionRepository repository;

    public void saveActionType(UserActionAvro userActionAvro) {
        UserAction userAction = new UserAction();
        userAction.setUserId(userAction.getUserId());
        userAction.setEventId(userAction.getEventId());
        userAction.setActionType(ActionType.valueOf(userActionAvro.getActionType().name()));
        userAction.setActionTime(userActionAvro.getTimestamp());

        repository.save(userAction);
    }
}
