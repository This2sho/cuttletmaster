package PorkCutlet.master.controller;

import PorkCutlet.master.controller.dto.CommentDto;
import PorkCutlet.master.controller.dto.UserInfoDto;
import PorkCutlet.master.controller.auth.Login;
import PorkCutlet.master.domain.Comment;
import PorkCutlet.master.service.CommentService;
import com.mysema.commons.lang.Pair;
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

import static PorkCutlet.master.controller.PageConst.commentsPageSize;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("reviews/{reviewId}/comments")
    public String getComments(@Login UserInfoDto user, @PathVariable Long reviewId, Model model,
                              @PageableDefault(size = commentsPageSize) Pageable pageable) {
        List<CommentDto> comments = commentService.getCommentsWithFetchJoin(pageable, reviewId)
                .stream().map(CommentDto::from).collect(Collectors.toList());

        model.addAttribute("firstLoad", false);
        model.addAttribute("comments", comments);
        model.addAttribute("totalPage", commentService.getTotalPage(reviewId));

        return "fragments/comments";
    }

    @PostMapping("reviews/{reviewId}/comments")
    public String createComment(@Login UserInfoDto user, @PathVariable Long reviewId, String content, Model model) {
        commentService.createComment(user.getId(), reviewId, content);
        Pair<Integer, List<Comment>> result = commentService.getLastCommentsWithTotalPage(reviewId);
        List<CommentDto> comments = result.getSecond()
                .stream().map(CommentDto::from).collect(Collectors.toList());

        model.addAttribute("firstLoad", false);
        model.addAttribute("comments", comments);
        model.addAttribute("totalPage", result.getFirst());
        return "fragments/comments";
    }
}
