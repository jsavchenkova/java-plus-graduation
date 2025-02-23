package ewm.request.service;

import ewm.error.exception.ConflictException;
import ewm.error.exception.NotFoundException;
//import ewm.event.EventRepository;
import ewm.model.Event;
import ewm.model.EventState;
import ewm.dto.request.RequestDto;
import ewm.request.mapper.RequestMapper;
import ewm.model.Request;
import ewm.model.RequestStatus;
import ewm.request.repository.RequestRepository;
//import ewm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final  RequestRepository requestRepository;
//    @Autowired
//    private  EventRepository eventRepository;
//    private final UserRepository userRepository;

    @Override
    public List<RequestDto> getRequests(Long userId) {
//        getUser(userId);
        return RequestMapper.INSTANCE.mapListRequests(requestRepository.findAllByRequesterId(userId));
    }

    @Transactional
    @Override
    public RequestDto createRequest(Long userId, Long eventId) {
        Event event = getEvent(eventId);
//        User user = getUser(userId);
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
//            eventRepository.save(event);
        }
        return RequestMapper.INSTANCE.mapToRequestDto(request);
    }

    @Transactional
    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
//        getUser(userId);
        Request request = getRequest(requestId);
        if (!request.getRequesterId().equals(userId))
            throw new ConflictException("Другой пользователь не может отменить запрос");
        request.setStatus(RequestStatus.CANCELED);
        requestRepository.save(request);
        Event event = getEvent(request.getEventId());
        event.setConfirmedRequests(event.getConfirmedRequests() - 1);
//        eventRepository.save(event);
        return RequestMapper.INSTANCE.mapToRequestDto(request);
    }

    private void checkRequest(Long userId, Event event) {
        if (!requestRepository.findAllByRequesterIdAndEventId(userId, event.getId()).isEmpty())
            throw new ConflictException("нельзя добавить повторный запрос");
        if (event.getInitiatorId().equals(userId))
            throw new ConflictException("инициатор события не может добавить запрос на участие в своём событии");
        if (!event.getState().equals(EventState.PUBLISHED))
            throw new ConflictException("нельзя участвовать в неопубликованном событии");
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(event.getConfirmedRequests()))
            throw new ConflictException("у события достигнут лимит запросов на участие");
    }

    private Event getEvent(Long eventId) {
//        Optional<Event> event = eventRepository.findById(eventId);
//        if (event.isEmpty())
//            throw new NotFoundException("События с id = " + eventId.toString() + " не существует");
//        return event.get();
        return null;
    }

//    private User getUser(Long userId) {
////        Optional<User> user = userRepository.findById(userId);
////        if (user.isEmpty())
////            throw new NotFoundException("Пользователя с id = " + userId.toString() + " не существует");
////        return user.get();
//        return null;
//    }

    private Request getRequest(Long requestId) {
        Optional<Request> request = requestRepository.findById(requestId);
        if (request.isEmpty())
            throw new NotFoundException("Запроса с id = " + requestId.toString() + " не существует");
        return request.get();
    }
}
