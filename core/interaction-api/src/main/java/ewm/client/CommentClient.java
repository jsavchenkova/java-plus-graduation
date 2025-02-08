package ewm.client;

import ewm.dto.comment.CommentDto;
import ewm.dto.comment.CreateCommentDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "comment-client")
public interface CommentClient {
    String PRIVATE_PATH = "/users/{userId}/events/{eventId}/comments";
    String PUBLIC_PATH = "/events/{eventId}/comments";

    @GetMapping(PUBLIC_PATH)
    List<CommentDto> getEventComments(@PathVariable Long eventId);

    @GetMapping(PRIVATE_PATH)
    List<CommentDto> getEventCommentsByUserId(@PathVariable Long userId, @PathVariable Long eventId);

    @PostMapping(PRIVATE_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    CommentDto addComment(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid CreateCommentDto createCommentDto
    );

    @GetMapping(PUBLIC_PATH + "/{commentId}")
    CommentDto getComment(
            @PathVariable Long eventId,
            @PathVariable Long commentId
    );
}
