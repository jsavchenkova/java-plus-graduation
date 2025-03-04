package ewm.comment.service;

import ewm.client.UserClient;
import ewm.comment.mapper.CommentMapper;
import ewm.comment.model.Comment;
import ewm.comment.repository.CommentRepository;
import ewm.dto.comment.CommentDto;
import ewm.dto.comment.CreateCommentDto;
import ewm.dto.user.UserDto;
import ewm.error.exception.ConflictException;
import ewm.error.exception.NotFoundException;
import ewm.event.EventRepository;
import ewm.event.model.Event;
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
    private final UserClient userClient;

    @Override
    @Transactional
    public CommentDto addComment(Long userId, Long eventId, CreateCommentDto createCommentDto) {
        UserDto user = userClient.getUserById(userId);
        Event event = getEventById(eventId);

        Comment comment = Comment.builder()
                .authorId(userId)
                .event(event)
                .content(createCommentDto.getContent())
                .build();

        Comment saved = commentRepository.save(comment);
        return CommentMapper.INSTANCE.commentToCommentDto(saved);
    }

    @Override
    public CommentDto getComment(Long eventId, Long commentId) {
        getEventById(eventId);
        return CommentMapper.INSTANCE.commentToCommentDto(getCommentById(commentId));
    }

    @Override
    public List<CommentDto> getEventCommentsByUserId(Long userId, Long eventId) {
        userClient.getUserById(userId);
        getEventById(eventId);
        return commentRepository.findAllByEventIdAndAuthorId(eventId, userId)
                .stream()
                .map(CommentMapper.INSTANCE::commentToCommentDto)
                .toList();
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
        UserDto user = userClient.getUserById(userId);
        if (!Objects.equals(comment.getAuthorId(), user.getId())) {
            throw new ConflictException("This user can't update comment");
        }
        comment.setContent(createCommentDto.getContent());
        return CommentMapper.INSTANCE.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        userClient.getUserById(userId);
        Comment comment = getCommentById(commentId);
        UserDto user = userClient.getUserById(userId);
        if (!Objects.equals(comment.getAuthorId(), user.getId())) {
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
