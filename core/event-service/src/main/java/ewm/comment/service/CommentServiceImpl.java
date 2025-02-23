package ewm.comment.service;

import ewm.client.UserAdminClient;
import ewm.dto.comment.CommentDto;
import ewm.dto.comment.CreateCommentDto;
import ewm.comment.mapper.CommentMapper;
import ewm.dto.user.UserDto;
import ewm.event.repository.EventRepository;
import ewm.model.Comment;
import ewm.comment.repository.CommentRepository;
import ewm.error.exception.ConflictException;
import ewm.error.exception.NotFoundException;

import ewm.model.Event;
//import ewm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final String COMMENT_NOT_FOUND = "Comment not found";

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserAdminClient userAdminClient;

    @Override
    @Transactional
    public CommentDto addComment(Long userId, Long eventId, CreateCommentDto createCommentDto) {
        UserDto userDto = userAdminClient.getUserById(userId);
        Event event = getEventById(eventId);

        Comment comment = Comment.builder()
                .event(event)
                .content(createCommentDto.getContent())
                .build();

        Comment saved = commentRepository.save(comment);
        CommentDto dto = CommentMapper.INSTANCE.commentToCommentDto(saved);
        dto.setAuthor(userDto);
        return dto;
    }

    @Override
    public CommentDto getComment(Long eventId, Long commentId) {
        getEventById(eventId);
        Comment comment = getCommentById(commentId);
        UserDto userDto = userAdminClient.getUserById(comment.getAuthorId());
        CommentDto dto = CommentMapper.INSTANCE.commentToCommentDto(comment);
        dto.setAuthor(userDto);
        return dto;
    }

    @Override
    public List<CommentDto> getEventCommentsByUserId(Long userId, Long eventId) {
        UserDto userDto = userAdminClient.getUserById(userId);
        getEventById(eventId);
        List<CommentDto> dto = commentRepository.findAllByEventIdAndAuthorId(eventId, userId)
                .stream()
                .map(CommentMapper.INSTANCE::commentToCommentDto)
                .toList();
        dto.stream().forEach(x-> x.setAuthor(userDto));
        return dto;
    }

    @Override
    public List<CommentDto> getEventComments(Long eventId) {
        getEventById(eventId);
        return commentRepository.findAllByEventId(eventId)
                .stream()
                .map(CommentMapper.INSTANCE::commentToCommentDto)
                .toList();
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long userId, Long eventId, Long commentId, CreateCommentDto createCommentDto) {
        getEventById(eventId);
        Comment comment = getCommentById(commentId);
        userAdminClient.getUserById(userId);
        if (!Objects.equals(comment.getAuthorId(), userId)) {
            throw new ConflictException("This user can't update comment");
        }
        comment.setContent(createCommentDto.getContent());
        return CommentMapper.INSTANCE.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        getEventById(eventId);
        Comment comment = getCommentById(commentId);
        userAdminClient.getUserById(userId);
        if (!Objects.equals(comment.getAuthorId(), userId)) {
            throw new ConflictException("This user can't delete comment");
        }
        commentRepository.deleteById(commentId);
    }

    private Event getEventById(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            throw new NotFoundException("Event not found");
        }
        return optionalEvent.get();
    }

    private Comment getCommentById(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new NotFoundException(COMMENT_NOT_FOUND);
        }
        return optionalComment.get();
    }
}
