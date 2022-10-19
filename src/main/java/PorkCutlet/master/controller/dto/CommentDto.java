package PorkCutlet.master.controller.dto;

import PorkCutlet.master.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String nickName;

    public static CommentDto from(Comment comment) {
        return new CommentDto(comment.getId(), comment.getContent(), comment.getUser().getNickName());
    }
}
