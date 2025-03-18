package ru.practicum.ewm.model;

import lombok.Data;

@Data
public class SimilarEventsRequest {

    private Long eventId;
    private Long userId;
    private Long maxResults;
}
