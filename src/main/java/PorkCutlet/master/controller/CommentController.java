package PorkCutlet.master.controller;

import PorkCutlet.master.controller.dto.CommentDto;
import PorkCutlet.master.controller.dto.UserInfoDto;
import PorkCutlet.master.controller.login.Login;
import PorkCutlet.master.service.CommentService;
import PorkCutlet.master.service.ReviewService;
import PorkCutlet.master.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("reviews/{reviewId}/comments")
    public String getComments(@PathVariable Long reviewId, @PageableDefault(size = 4)
            Pageable pageable, Model model) {
        List<CommentDto> comments = commentService.getCommentsWithPage(pageable, reviewId).getContent()
                .stream().map(CommentDto::from).collect(Collectors.toList());
        model.addAttribute("comments", comments);
        return "fragments/comments";
    }

    @PostMapping("reviews/{reviewId}/comments")
    public String createComment(@Login UserInfoDto user, @PathVariable Long reviewId, String content, Model model) {
        commentService.createComment(user.getId(), reviewId, content);
        List<CommentDto> comments = commentService.getComments(reviewId)
                .stream().map(CommentDto::from).collect(Collectors.toList());
        model.addAttribute("comments", comments);
        return "fragments/comments";
    }
}
