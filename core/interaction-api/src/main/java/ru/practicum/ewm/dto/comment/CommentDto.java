package ru.practicum.ewm.dto.comment;

import ru.practicum.ewm.dto.user.UserDto;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private UserDto author;
}
