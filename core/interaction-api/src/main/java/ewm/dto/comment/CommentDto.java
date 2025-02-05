package ewm.dto.comment;

import lombok.Data;
import ewm.model.User;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private User author;
}
