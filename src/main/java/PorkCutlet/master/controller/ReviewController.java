package PorkCutlet.master.controller;

import PorkCutlet.master.controller.login.Login;
import PorkCutlet.master.domain.*;
import PorkCutlet.master.ImageStore;
import PorkCutlet.master.service.ReviewService;
import PorkCutlet.master.service.UserService;
import PorkCutlet.master.validation.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reviews")
public class ReviewController {
    private final UserService userService;
    private final ReviewService reviewService;
    private final ImageStore imageStore;

    private final FileValidator fileValidator;

    @GetMapping
    public String reviewList(@Login UserDto user) {

        return "reviews/reviewList";
    }


    @GetMapping("/new")
    public String createReviewForm(@Login UserDto user, ReviewDto reviewDto) {
        if(!user.isAdmin()) return "reviews/reviewList";
        return "reviews/reviewForm";
    }

    @PostMapping("/new")
    public String createReview(@Login UserDto user,
                               @Valid ReviewDto reviewDto,
                               BindingResult bindingResult) throws IOException {
        if(!user.isAdmin()) return "reviews/reviewList";
        List<MultipartFile> files = reviewDto.getImageFiles();
        fileValidator.validate(files, bindingResult);
        if (bindingResult.hasErrors()) {return "reviews/reviewForm";}

        List<Image> images = imageStore.storeImages(files);


        //user 어떻게 넣을지 생각해야함.
        User loginUser = userService.login(user.getLoginId(), user.getPassword());
        Review review = makeReview(reviewDto, images, loginUser);
        reviewService.createReview(review);
        log.info("===== create review!! =====");
        return "redirect:/reviews";
    }

    private Review makeReview(ReviewDto reviewDto, List<Image> images, User loginUser) {
        Review review = new Review(loginUser, reviewDto.makeRestaurant(), images, reviewDto.getContent(), reviewDto.makeRatingInfo());
        return review;
    }
}
