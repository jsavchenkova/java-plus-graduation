package ru.practicum.ewm.model;

import lombok.Data;

import java.time.Instant;

@Data
public class UserAction {
    private Long id;
    private Long userId;
    private Long eventId;
    private ActionType actionType;
    private Instant actionTime;
}
