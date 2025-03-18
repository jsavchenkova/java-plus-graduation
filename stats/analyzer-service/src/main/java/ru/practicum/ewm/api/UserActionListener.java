package ru.practicum.ewm.api;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.service.UserActionService;
import ru.practicum.ewm.stats.avro.UserActionAvro;

@Component
@RequiredArgsConstructor
public class UserActionListener {

    private UserActionService service;

    @KafkaListener(topics = "${kafka.topic}",
            containerFactory = "eventSimilarityListenerContainerFactory",
            autoStartup = "true")
    public void listen(UserActionAvro message) {
        service.saveActionType(message);
    }
}
