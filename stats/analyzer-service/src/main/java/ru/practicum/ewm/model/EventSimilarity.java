package ru.practicum.ewm.model;

import lombok.Data;

import java.time.Instant;

@Data
public class EventSimilarity {
    private Long id;
    private Long eventA;
    private Long eventB;
    private Double score;
    private Instant eventTime;
}
