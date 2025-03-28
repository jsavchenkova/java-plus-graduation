package ru.practicum.ewm.service;

import com.google.protobuf.Timestamp;
import ru.practicum.ewm.client.EventClient;
import ru.practicum.ewm.client.UserClient;
import ru.practicum.ewm.dto.event.EventDto;
import ru.practicum.ewm.dto.request.RequestDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.enums.EventState;
import ru.practicum.ewm.enums.RequestStatus;
import ru.practicum.ewm.error.exception.ConflictException;
import ru.practicum.ewm.error.exception.NotFoundException;
import ru.practicum.ewm.grpc.CollectorClient;
import ru.practicum.ewm.grpc.stats.action.ActionTypeProto;
import ru.practicum.ewm.grpc.stats.action.UserActionProto;
import ru.practicum.ewm.mapper.ReqMapper;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j

@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserClient userClient;
    private final EventClient eventClient;
    private final CollectorClient collectorClient;

    @Override
    public List<RequestDto> getRequests(Long userId) {
        userClient.getUserById(userId);
        return ReqMapper.mapListRequests(requestRepository.findAllByRequesterId(userId));
    }

    @Transactional
    @Override
    public RequestDto createRequest(Long userId, Long eventId) {
        EventDto event = eventClient.getEventById(eventId);
        if (event == null) {
            throw new ConflictException("Нет события в нужном статусе");
        }
        UserDto user = userClient.getUserById(userId);
        checkRequest(userId, event);
        Request request = Request.builder()
                .requesterId(userId)
                .created(LocalDateTime.now())
                .status(!event.getRequestModeration()
                        || event.getParticipantLimit() == 0
                        ? RequestStatus.CONFIRMED : RequestStatus.PENDING)
                .eventId(eventId)
                .build();
        request = requestRepository.save(request);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventClient.updateConfirmRequests(eventId, event);
        }
        try {
            Instant instant = LocalDateTime.now().toInstant(ZoneOffset.UTC);

            Timestamp t = Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano())
                    .build();
            collectorClient.collectUserAction(UserActionProto.newBuilder()
                    .setActionType(ActionTypeProto.ACTION_REGISTER)
                    .setUserId(userId)
                    .setEventId(eventId)
                    .setTimestamp(t)
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ReqMapper.mapToRequestDto(request);
    }

    @Transactional
    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        UserDto user = userClient.getUserById(userId);
        Request request = getRequest(requestId);
        if (!request.getRequesterId().equals(userId))
            throw new ConflictException("Другой пользователь не может отменить запрос");
        request.setStatus(RequestStatus.CANCELED);
        requestRepository.save(request);
        EventDto event = eventClient.getEventById(request.getEventId());
        event.setConfirmedRequests(event.getConfirmedRequests() - 1);
        eventClient.updateConfirmRequests(event.getId(), event);
        return ReqMapper.mapToRequestDto(request);
    }

    public List<RequestDto> findAllById(List<Long> ids) {

        return ReqMapper.mapListRequests(requestRepository.findAllById(ids));
    }

    public List<RequestDto> findAllByEventId(Long eventId) {
        return ReqMapper.mapListRequests(requestRepository.findAllByEventId(eventId));
    }

    public RequestDto updateRequest(RequestDto requestDto) {
        Request request = ReqMapper.mapDtoToRequest(requestDto);
        request = requestRepository.save(request);
        return ReqMapper.mapToRequestDto(request);
    }

    public List<RequestDto> updateAllRequests(List<RequestDto> requestDtoList) {
        List<RequestDto> list = new ArrayList<>(requestDtoList.size());
        for (RequestDto r : requestDtoList) {
            list.add(updateRequest(r));
        }
        return list;
    }

    private void checkRequest(Long userId, EventDto event) {
        if (!requestRepository.findAllByRequesterIdAndEventId(userId, event.getId()).isEmpty())
            throw new ConflictException("нельзя добавить повторный запрос");
        if (event.getInitiator().getId().equals(userId))
            throw new ConflictException("инициатор события не может добавить запрос на участие в своём событии");
        if (!event.getState().equals(EventState.PUBLISHED.toString()))
            throw new ConflictException("нельзя участвовать в неопубликованном событии");
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(event.getConfirmedRequests()))
            throw new ConflictException("у события достигнут лимит запросов на участие");
    }

    private Request getRequest(Long requestId) {
        Optional<Request> request = requestRepository.findById(requestId);
        if (request.isEmpty())
            throw new NotFoundException("Запроса с id = " + requestId.toString() + " не существует");
        return request.get();
    }


}
