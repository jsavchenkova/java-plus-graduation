package ru.practicum.ewm.api;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.UserActionAvro;

@Component
@RequiredArgsConstructor
public class UserActionListener {

    @KafkaListener(topics = "${kafka.topic}")
    public void listen(UserActionAvro message) {

    }
}
