package ru.practicum.ewm.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.HashMap;
import java.util.Map;

@Service
public class AggregatorService {
    Map<Long, Map<Long, Double>> eventWeight;
    Map<Long, Double> eventWeightSum;
    Map<Long, Map<Long, Double>> minWeightsSum;

    public AggregatorService() {
        eventWeight = new HashMap<>();
        eventWeightSum = new HashMap<>();
        minWeightsSum = new HashMap<>();
    }

    public void calculateWeight(UserActionAvro userActionAvro) {
        double weiht = 0;
        switch (userActionAvro.getActionType()) {
            case LIKE -> weiht = 1;
            case REGISTER -> weiht = 0.8;
            case VIEW -> weiht = 0.4;
        }
        if (eventWeight.containsKey(userActionAvro.getEventId())) {
            if (eventWeight.get(userActionAvro.getEventId()).containsKey(userActionAvro.getUserId())) {
                eventWeight.get(userActionAvro.getEventId())
                        .put(userActionAvro.getUserId(),
                                Math.min(weiht, eventWeight.get(userActionAvro.getEventId()).get(userActionAvro.getUserId())));
            } else return;
        } else {
            eventWeight.put(userActionAvro.getEventId(), new HashMap<>());
            eventWeight.get(userActionAvro.getEventId()).put(userActionAvro.getUserId(), weiht);
        }

        Double sum = 0d;
        for (Double d : eventWeight.get(userActionAvro.getEventId()).values()) {
            sum += d;
        }

        eventWeightSum.put(userActionAvro.getEventId(), sum);


        for (Long l : eventWeight.keySet()) {
            if (l == userActionAvro.getEventId()) continue;
            if (eventWeight.get(l).containsKey(userActionAvro.getUserId())) {
                Double minW = Math.min(eventWeight.get(l).get(userActionAvro.getUserId()),
                        eventWeight.get(userActionAvro.getEventId()).get(userActionAvro.getUserId()));
                put(userActionAvro.getEventId(), l, minW);
            }
        }
    }

    public void put(long eventA, long eventB, double sum) {
        long first = Math.min(eventA, eventB);
        long second = Math.max(eventA, eventB);

        minWeightsSum
                .computeIfAbsent(first, e -> new HashMap<>())
                .put(second, sum);
    }

    public double get(long eventA, long eventB) {
        long first = Math.min(eventA, eventB);
        long second = Math.max(eventA, eventB);

        return minWeightsSum
                .computeIfAbsent(first, e -> new HashMap<>())
                .getOrDefault(second, 0.0);
    }
}
