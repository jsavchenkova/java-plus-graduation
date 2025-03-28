package ru.practicum.ewm.controller;

import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CreateCommentDto;
import ru.practicum.ewm.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private static final String PRIVATE_PATH = "/users/{userId}/events/{eventId}/comments";
    private static final String PUBLIC_PATH = "/events/{eventId}/comments";

    private final CommentService commentService;


    @GetMapping(PUBLIC_PATH)
    public List<CommentDto> getEventComments(@PathVariable Long eventId) {
        return commentService.getEventComments(eventId);
    }

    @GetMapping(PRIVATE_PATH)
    public List<CommentDto> getEventCommentsByUserId(@PathVariable Long userId, @PathVariable Long eventId) {
        return commentService.getEventCommentsByUserId(userId, eventId);
    }

    @PostMapping(PRIVATE_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid CreateCommentDto createCommentDto
    ) {
        return commentService.addComment(userId, eventId, createCommentDto);
    }

    @GetMapping(PUBLIC_PATH + "/{commentId}")
    public CommentDto getComment(
            @PathVariable Long eventId,
            @PathVariable Long commentId
    ) {
        return commentService.getComment(eventId, commentId);
    }


    @PatchMapping(PRIVATE_PATH + "/{commentId}")
    public CommentDto updateComment(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long commentId,
            @RequestBody @Valid CreateCommentDto createCommentDto
    ) {
        return commentService.updateComment(userId, eventId, commentId, createCommentDto);
    }

    @DeleteMapping(PRIVATE_PATH + "/{commentId}")
    public void deleteComment(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(userId, eventId, commentId);
    }
}
