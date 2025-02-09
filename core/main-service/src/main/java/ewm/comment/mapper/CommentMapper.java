package ewm.comment.mapper;

import ewm.dto.comment.CommentDto;
import ewm.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto commentToCommentDto(Comment comment);
}
