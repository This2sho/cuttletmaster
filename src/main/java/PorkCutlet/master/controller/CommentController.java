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
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static PorkCutlet.master.controller.PageConst.commentsPageSize;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/reviews/{reviewId}/comments")
    public String getComments(@Login UserInfoDto user, @PathVariable Long reviewId, Model model,
                              @PageableDefault(size = commentsPageSize) Pageable pageable) {
        List<Comment> commentsWithFetchJoin = commentService.getCommentsWithFetchJoin(pageable, reviewId);
        List<CommentDto> comments = commentsWithFetchJoin
                .stream().map(CommentDto::from).collect(Collectors.toList());

        String reviewCreator = commentsWithFetchJoin.size() > 0 ? commentsWithFetchJoin.get(0).getReview().getUser().getNickName() : "";
        model.addAttribute("firstLoad", false);
        model.addAttribute("comments", comments);
        model.addAttribute("totalPage", commentService.getTotalPage(reviewId));
        model.addAttribute("presentPage", pageable.getPageNumber());
        model.addAttribute("reviewCreator", reviewCreator);
        return "fragments/comments";
    }

    @PostMapping("/reviews/{reviewId}/comments")
    public String createComment(@Login UserInfoDto user, @PathVariable Long reviewId, String content, Model model) {
        if(user == null){
            return "CONST-ERROR";
        }
        commentService.createComment(user.getId(), reviewId, content);
        return getLastComment(reviewId, model);
    }

    @PutMapping("/reviews/{reviewId}/comments/{commentId}")
    public String updateComment(@Login UserInfoDto user, @PathVariable Long reviewId, @PathVariable Long commentId, String content, int pageNum, Model model) throws AccessException {
        if(user == null){
            return "CONST-ERROR";
        }
        commentService.updateComment(user.getId(), commentId, content);
        return getPresentComment(reviewId, pageNum, model);
    }

    @DeleteMapping("/reviews/{reviewId}/comments/{commentId}")
    public String deleteComment(@Login UserInfoDto user, @PathVariable Long reviewId, @PathVariable Long commentId, int pageNum, Model model) throws AccessException {
        if(user == null){
            return "CONST-ERROR";
        }
        commentService.deleteComment(commentId);
        return getPresentComment(reviewId, pageNum, model);
    }

    private String getLastComment(Long reviewId, Model model) {
        Pair<Integer, List<Comment>> result = commentService.getLastCommentsWithTotalPage(reviewId);
        List<Comment> comments = result.getSecond();
        List<CommentDto> commentsDto = comments
                .stream().map(CommentDto::from).collect(Collectors.toList());

        String reviewCreator = comments.size() > 0 ? comments.get(0).getReview().getUser().getNickName() : "";

        model.addAttribute("firstLoad", false);
        model.addAttribute("comments", commentsDto);
        model.addAttribute("totalPage", result.getFirst());
        model.addAttribute("presentPage", result.getFirst());
        model.addAttribute("reviewCreator", reviewCreator);
        return "fragments/comments";
    }

    private String getPresentComment(Long reviewId, int pageNum, Model model) {
        Pair<Integer, List<Comment>> result = commentService.getPresentCommentsWithTotalPage(reviewId, pageNum);
        List<Comment> comments = result.getSecond();
        List<CommentDto> commentsDto = comments
                .stream().map(CommentDto::from).collect(Collectors.toList());

        String reviewCreator = comments.size() > 0 ? comments.get(0).getReview().getUser().getNickName() : "";

        model.addAttribute("firstLoad", false);
        model.addAttribute("comments", commentsDto);
        model.addAttribute("totalPage", result.getFirst());
        model.addAttribute("presentPage", pageNum > result.getFirst() ? result.getFirst() : pageNum);
        model.addAttribute("reviewCreator", reviewCreator);
        return "fragments/comments";
    }
}
