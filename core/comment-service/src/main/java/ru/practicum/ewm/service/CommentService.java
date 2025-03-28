package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CreateCommentDto;

import java.util.List;

// Управление комментариями
public interface CommentService {
    CommentDto addComment(Long userId, Long eventId, CreateCommentDto createCommentDto);

    CommentDto getComment(Long eventId, Long commentId);

    List<CommentDto> getEventCommentsByUserId(Long userId, Long eventId);

    List<CommentDto> getEventComments(Long eventId);

    CommentDto updateComment(Long userId, Long eventId, Long commentId, CreateCommentDto createCommentDto);

    void deleteComment(Long userId, Long eventId, Long commentId);
}
