package PorkCutlet.master.controller;

import PorkCutlet.master.controller.dto.*;
import PorkCutlet.master.controller.login.Login;
import PorkCutlet.master.domain.*;
import PorkCutlet.master.ImageStore;
import PorkCutlet.master.service.CommentService;
import PorkCutlet.master.service.LikeService;
import PorkCutlet.master.service.ReviewService;
import PorkCutlet.master.service.UserService;
import PorkCutlet.master.validation.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static PorkCutlet.master.controller.PageConst.commentsPageSize;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reviews")
public class ReviewController {
    private final UserService userService;
    private final ReviewService reviewService;
    private final LikeService likeService;
    private final ImageStore imageStore;
    private final CommentService commentService;

    private final FileValidator fileValidator;

    @GetMapping("/{reviewId}")
    public String reviewDetail(@Login UserInfoDto user, @PathVariable Long reviewId,
                               Model model) {
        Review review = reviewService.getReviewByIdWithFetchJoin(reviewId);

        model.addAttribute("review" ,DetailReviewDto.from(review));
        model.addAttribute("likes", userLikesReview(user, reviewId));

        Long likesNum = likeService.countLikesNum(reviewId);
        model.addAttribute("likesNum", likesNum);

        PageRequest request = PageRequest.of(0, commentsPageSize);

        List<CommentDto> comments = commentService.getCommentsWithFetchJoin(request, reviewId)
                .stream().map(CommentDto::from).collect(Collectors.toList());

        model.addAttribute("comments", comments);
        model.addAttribute("totalPage", commentService.getTotalPage(reviewId));

        return "reviews/reviewDetail";
    }

    @GetMapping
    public String reviewList(@Login UserInfoDto user, @PageableDefault(size = 4)
            Pageable pageable, Model model) {
        List<ThumbNailReviewDto> reviewList = reviewService.getPagingReview(pageable).getContent()
                .stream().map(ThumbNailReviewDto::from).collect(Collectors.toList());
        model.addAttribute("reviewList", reviewList);
        return "reviews/reviewList";
    }

    @GetMapping("/new")
    public String createReviewForm(@Login UserInfoDto user, CreateReviewForm createReviewForm) {
        if(!user.isAdmin()) return "reviews/reviewList";
        return "reviews/reviewForm";
    }

    @PostMapping("/new")
    public String createReview(@Login UserInfoDto user, @Valid CreateReviewForm createReviewForm,
                               BindingResult bindingResult) throws IOException {
        if(!user.isAdmin()) return "reviews/reviewList";
        List<MultipartFile> files = createReviewForm.getImageFiles();
        fileValidator.validate(files, bindingResult);
        if (bindingResult.hasErrors()) {return "reviews/reviewForm";}

        List<Image> images = imageStore.storeImages(files);

        User loginUser = userService.findById(user.getId()).orElse(null);
        Review review = makeReview(createReviewForm, images, loginUser);
        reviewService.createReview(review);
        return "redirect:/reviews";
    }

    private Review makeReview(CreateReviewForm createReviewForm, List<Image> images, User loginUser) {
        Review review = new Review(loginUser, createReviewForm.makeRestaurant(), images, createReviewForm.getContent(), createReviewForm.getOneSentence(), createReviewForm.makeRatingInfo());
        return review;
    }

    private boolean userLikesReview(UserInfoDto user, Long reviewId) {
        return user != null && likeService.doExistLike(user.getId(), reviewId);
    }
}
