package PorkCutlet.master.controller.dto;

import PorkCutlet.master.domain.Comment;
import PorkCutlet.master.domain.User;
import PorkCutlet.master.domain.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String nickName;
    private Boolean isAdmin;

    public static CommentDto from(Comment comment) {
        User user = comment.getUser();
        return new CommentDto(comment.getId(), comment.getContent(), user.getNickName(), user.getUserType().isAdmin());
    }
}
