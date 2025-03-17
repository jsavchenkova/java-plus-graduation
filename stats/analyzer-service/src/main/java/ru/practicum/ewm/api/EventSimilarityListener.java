package ru.practicum.ewm.api;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

@Component
public class EventSimilarityListener {

    @KafkaListener(topics = "${kafka.topic-sums}")
    public void listen(EventSimilarityAvro message) {

    }
}
