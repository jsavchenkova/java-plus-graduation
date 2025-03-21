package ru.practicum.ewm.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.service.UserActionService;
import ru.practicum.ewm.stats.avro.UserActionAvro;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActionListener {

    private UserActionService service;

    @KafkaListener(topics = "${kafka.topic}",
            containerFactory = "eventSimilarityListenerContainerFactory",
            autoStartup = "true")
    public void listen(UserActionAvro message) {
        log.info("Получено сообщение: {}", message.toString());
        service.saveActionType(message);
    }
}
