package PorkCutlet.master.controller;

import PorkCutlet.master.controller.dto.CreateReviewForm;
import PorkCutlet.master.controller.dto.DetailReviewDto;
import PorkCutlet.master.controller.dto.ThumbNailReviewDto;
import PorkCutlet.master.controller.dto.UserDto;
import PorkCutlet.master.controller.login.Login;
import PorkCutlet.master.domain.*;
import PorkCutlet.master.ImageStore;
import PorkCutlet.master.service.ReviewService;
import PorkCutlet.master.service.UserService;
import PorkCutlet.master.validation.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reviews")
public class ReviewController {
    private final UserService userService;
    private final ReviewService reviewService;
    private final ImageStore imageStore;

    private final FileValidator fileValidator;

    @GetMapping("/{reviewId}")
    public String reviewDetail(@Login UserDto user, @PathVariable Long reviewId,
                               Model model) {
        model.addAttribute("review" ,DetailReviewDto.from(reviewService.getReviewById(reviewId)));
        /**
         * todo
         * 좋아요, 댓글 추가 구현
         */
        return "reviews/reviewDetail";
    }
    @GetMapping
    public String reviewList(@Login UserDto user, @PageableDefault(size = 4)
            Pageable pageable, Model model) {
        List<ThumbNailReviewDto> reviewList = reviewService.getPagingReview(pageable).getContent()
                .stream().map(ThumbNailReviewDto::from).collect(Collectors.toList());
        model.addAttribute("reviewList", reviewList);
        return "reviews/reviewList";
    }

    @GetMapping("/new")
    public String createReviewForm(@Login UserDto user, CreateReviewForm createReviewForm) {
        if(!user.isAdmin()) return "reviews/reviewList";
        return "reviews/reviewForm";
    }

    @PostMapping("/new")
    public String createReview(@Login UserDto user, @Valid CreateReviewForm createReviewForm,
                               BindingResult bindingResult) throws IOException {
        if(!user.isAdmin()) return "reviews/reviewList";
        List<MultipartFile> files = createReviewForm.getImageFiles();
        fileValidator.validate(files, bindingResult);
        if (bindingResult.hasErrors()) {return "reviews/reviewForm";}

        List<Image> images = imageStore.storeImages(files);

        User loginUser = userService.login(user.getLoginId(), user.getPassword());
        Review review = makeReview(createReviewForm, images, loginUser);
        reviewService.createReview(review);
        return "redirect:/reviews";
    }

    private Review makeReview(CreateReviewForm createReviewForm, List<Image> images, User loginUser) {
        Review review = new Review(loginUser, createReviewForm.makeRestaurant(), images, createReviewForm.getContent(), createReviewForm.getOneSentence(), createReviewForm.makeRatingInfo());
        return review;
    }
}
