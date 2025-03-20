package ru.practicum.ewm.stats;

import net.devh.boot.grpc.client.inject.GrpcClient;
import ru.practicum.ewm.client.BaseClient;
import ru.practicum.ewm.dto.EndpointHitDTO;
import ru.practicum.ewm.dto.StatsRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.grpc.stats.analyzer.RecommendationsControllerGrpc;
import ru.practicum.ewm.grpc.stats.collector.UserActionControllerGrpc;
import ru.practicum.ewm.grpc.stats.recomendations.RecommendedEventProto;
import ru.practicum.ewm.grpc.stats.recomendations.SimilarEventsRequestProto;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class StatsClient extends BaseClient {

    @GrpcClient("analyzer-service")
    private RecommendationsControllerGrpc.RecommendationsControllerBlockingStub recomendationClient;

    @GrpcClient("collector-service")
    private UserActionControllerGrpc.UserActionControllerBlockingStub userActionClient;

    @Autowired
    public StatsClient(@Value("${stats.server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .build()
        );
    }

    public Stream<RecommendedEventProto> getSimilarEvents(long eventId, long userId, int maxResults) {
        SimilarEventsRequestProto request = SimilarEventsRequestProto.newBuilder()
                .setEventId(eventId)
                .setUserId(userId)
                .setMaxResults(maxResults)
                .build();

        // gRPC-метод getSimilarEvents возвращает Iterator, потому что в его схеме
        // мы указали, что он должен вернуть поток сообщений (stream stats.message.RecommendedEventProto)
        Iterator<RecommendedEventProto> iterator = recomendationClient.getSimilarEvents(request);

        // преобразуем Iterator в Stream
        return asStream(iterator);
    }

    private Stream<RecommendedEventProto> asStream(Iterator<RecommendedEventProto> iterator) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false
        );
    }


    public ResponseEntity<Object> saveHit(EndpointHitDTO requestDto) {
        return post("/hit", requestDto);
    }

    public ResponseEntity<Object> getStats(StatsRequestDTO headersDto) {
        Map<String, Object> params = Map.of(
                "start", headersDto.getStart(),
                "end", headersDto.getEnd(),
                "unique", headersDto.getUnique()
        );
        return get("/stats" + getUrlParams(headersDto), params);
    }

    private String getUrlParams(StatsRequestDTO headersDto) {

        String urls = String.join(",", headersDto.getUris());
        StringBuilder sb = new StringBuilder("?");
        return sb.append(getKeyValueUrl("start", headersDto.getStart()))
                .append("&")
                .append(getKeyValueUrl("end", headersDto.getEnd()))
                .append("&")
                .append(getKeyValueUrl("uris", urls))
                .append("&")
                .append(getKeyValueUrl("unique", headersDto.getUnique()))
                .toString();
    }

    private String getKeyValueUrl(String key, Object value) {
        return key + "=" + value;
    }
}
