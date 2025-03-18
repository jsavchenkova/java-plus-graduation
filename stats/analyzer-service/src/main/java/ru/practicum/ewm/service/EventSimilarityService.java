package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.grpc.stats.recomendations.RecommendedEventProto;
import ru.practicum.ewm.grpc.stats.recomendations.SimilarEventsRequestProto;
import ru.practicum.ewm.model.EventSimilarity;
import ru.practicum.ewm.model.SimilarEventsRequest;
import ru.practicum.ewm.repository.EventSimilarityRepository;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventSimilarityService {

    private final EventSimilarityRepository repository;

    public void saveEventSimilarity(EventSimilarityAvro eventSimilarityAvro) {
        EventSimilarity eventSimilarity = new EventSimilarity();
        eventSimilarity.setEventA(eventSimilarityAvro.getEventA());
        eventSimilarity.setEventB(eventSimilarityAvro.getEventB());
        eventSimilarity.setScore(eventSimilarityAvro.getScore());
        eventSimilarity.setEventTime(eventSimilarityAvro.getTimestamp());
        repository.save(eventSimilarity);
    }

    public List<RecommendedEventProto> getSimilarEvents(SimilarEventsRequestProto request) {
        SimilarEventsRequest similarEvent = new SimilarEventsRequest();
        similarEvent.setEventId(request.getEventId());
        similarEvent.setUserId(request.getUserId());
        similarEvent.setMaxResults(request.getMaxResults());

        List<EventSimilarity> list = repository.findByEventAOrEventB(request.getEventId(), request.getEventId())
                .stream()
                .filter(x ->
                        x.getEventA() != request.getEventId() &&
                                x.getEventB() != request.getEventId()).toList();
        list.sort((s1, s2) -> s2.getScore().compareTo(s1.getScore()));
        return list.stream()
                .limit(request.getMaxResults())
                .map(x -> {
                    Long id = x.getEventA();
                    if (id == request.getEventId()) id = x.getEventB();
                    RecommendedEventProto e = RecommendedEventProto.newBuilder()
                            .setEventId(id)
                            .setScore(x.getScore())
                            .build();
                    return e;
                })
                .toList();

    }
}
