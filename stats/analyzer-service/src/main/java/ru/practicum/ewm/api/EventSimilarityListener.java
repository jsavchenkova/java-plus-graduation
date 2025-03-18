package ru.practicum.ewm.api;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.service.EventSimilarityService;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

@Component
@RequiredArgsConstructor
public class EventSimilarityListener {

    private final EventSimilarityService service;

    @KafkaListener(topics = "${kafka.topic-sums}",
            containerFactory = "userActionListenerContainerFactory",
            autoStartup = "true")
    public void listen(EventSimilarityAvro message) {
        service.saveEventSimilarity(message);
    }
}
