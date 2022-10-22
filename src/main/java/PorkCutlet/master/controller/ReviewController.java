package PorkCutlet.master.controller;

import PorkCutlet.master.ImageUtils;
import PorkCutlet.master.controller.auth.Login;
import PorkCutlet.master.controller.dto.*;
import PorkCutlet.master.domain.Image;
import PorkCutlet.master.domain.Review;
import PorkCutlet.master.domain.User;
import PorkCutlet.master.service.*;
import PorkCutlet.master.validation.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import static PorkCutlet.master.controller.PageConst.reviewsPageSize;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reviews")
public class ReviewController {
    private final UserService userService;
    private final ReviewService reviewService;
    private final LikeService likeService;
    private final ImageUtils imageUtils;
    private final CommentService commentService;
    private final ImageService imageService;

    private final FileValidator fileValidator;

    @GetMapping
    public String reviewList(@Login UserInfoDto user, @PageableDefault(size = reviewsPageSize) Pageable pageable,
                             Model model) {
        List<ThumbNailReviewDto> reviewList = reviewService.getPagingReview(pageable)
                .stream().map(ThumbNailReviewDto::from).collect(Collectors.toList());
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("totalPage", reviewService.getTotalPage());
        model.addAttribute("firstLoad", false);
        model.addAttribute("presentPage", pageable.getPageNumber() == 0 ? 1 : pageable.getPageNumber());
        return "reviews/reviewList";
    }

    @GetMapping("/{reviewId}")
    public String reviewDetail(@Login UserInfoDto user, @PathVariable Long reviewId,
                               Model model) {
        Review review = reviewService.getReviewByIdWithFetchJoin(reviewId);

        DetailReviewDto detailReviewDto = DetailReviewDto.from(review);
        model.addAttribute("review" , detailReviewDto);
        model.addAttribute("likes", userLikesReview(user, reviewId));

        Long likesNum = likeService.countLikesNum(reviewId);
        model.addAttribute("likesNum", likesNum);

        PageRequest request = PageRequest.of(0, commentsPageSize);

        List<CommentDto> comments = commentService.getCommentsWithFetchJoin(request, reviewId)
                .stream().map(CommentDto::from).collect(Collectors.toList());

        model.addAttribute("firstLoad", true);
        model.addAttribute("comments", comments);
        model.addAttribute("totalPage", commentService.getTotalPage(reviewId));
        model.addAttribute("reviewCreator", detailReviewDto.getUserNickName());

        return "reviews/reviewDetail";
    }

    @DeleteMapping("/{reviewId}")
    public String deleteReview(@Login UserInfoDto user, @PathVariable Long reviewId, Model model) {
        if (noAuthReview(reviewId, user)) {
            return alertMessage("/reviews/" + reviewId, "리뷰를 삭제할 권한이 없습니다.",model);
        }
        Review review = reviewService.findById(reviewId).orElseThrow();
        imageUtils.deleteImageFilesByStoreFileName(review.getImages().stream().map(Image::getStoreImageName).collect(Collectors.toList()));
        reviewService.deleteReview(review);
        return "redirect:/reviews";
    }

    @GetMapping("/new")
    public String createReviewForm(@Login UserInfoDto user, ReviewForm reviewForm) {
        if(!user.isAdmin()) return "reviews/reviewList";
        return "reviews/reviewCreateForm";
    }

    @PostMapping("/new")
    @ResponseBody
    public Object createReview(@Login UserInfoDto user, @Valid ReviewForm reviewForm,
                               BindingResult bindingResult) throws IOException {
        if(!user.isAdmin()) return "reviews/reviewList";
        List<MultipartFile> files = reviewForm.getImageFiles();
        fileValidator.validate(files, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }

        List<Image> images = imageUtils.storeImages(files);

        User loginUser = userService.findById(user.getId()).orElse(null);
        Review review = makeReview(reviewForm, images, loginUser);
        reviewService.createReview(review);
        return "{\"result\":\"OK\"}";
    }

    @GetMapping("/{reviewId}/update")
    public String updateForm(@Login UserInfoDto user, @PathVariable Long reviewId, ReviewForm reviewForm,
                             Model model, BindingResult bindingResult) {
        if (noAuthReview(reviewId, user)) {
            return alertMessage("/reviews/" + reviewId, "리뷰를 수정할 권한이 없습니다.", model);
        }

        Review review = reviewService.findById(reviewId).orElseThrow();

        model.addAttribute("reviewForm", ReviewForm.from(review));
        return "reviews/reviewUpdateForm";
    }

    @PutMapping("/{reviewId}/update")
    @ResponseBody
    public Object updateReview(@Login UserInfoDto user, @PathVariable Long reviewId, @Valid ReviewForm reviewForm,
                               BindingResult bindingResult) throws IOException {
        List<MultipartFile> files = reviewForm.getImageFiles();
        List<String> preImages = reviewForm.getPreImages();
        List<String> deleteImages = reviewForm.getDeleteImages();

        validateFile(bindingResult, files, preImages, deleteImages);

        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }

        List<Image> updatedImages = getUpdatedImages(files, deleteImages, reviewId);

        reviewService.updateReview(reviewId, reviewForm.getForkCutletName(), reviewForm.makeForkCutletType(), reviewForm.getRestaurantName(),
                reviewForm.makeAddress(), reviewForm.getContent(), reviewForm.getOneSentence(), reviewForm.makeRatingInfo(), updatedImages);
        return "{\"result\":\"OK\"}";
    }

    private List<Image> getUpdatedImages(List<MultipartFile> files, List<String> deleteImages, Long reviewId) throws IOException {
        List<Image> images = imageUtils.updateImages(deleteImages, files);
        if (deleteImages != null) {
            for (String deleteImage : deleteImages) {
                imageService.deleteByStoreName(deleteImage);
            }
        }
        return images == null ? imageService.getImagesByReviewId(reviewId) : images;
    }

    private void validateFile(BindingResult bindingResult, List<MultipartFile> files, List<String> preImages, List<String> deleteImages) {
        if(preImages == null || deleteImages != null && preImages.size() == deleteImages.size()){
            fileValidator.validate(files, bindingResult);
        }
    }

    private Review makeReview(ReviewForm reviewForm, List<Image> images, User loginUser) {
        Review review = new Review(loginUser, reviewForm.makeRestaurant(), images, reviewForm.getContent(), reviewForm.getOneSentence(), reviewForm.makeRatingInfo());
        return review;
    }

    private boolean userLikesReview(UserInfoDto user, Long reviewId) {
        return user != null && likeService.doExistLike(user.getId(), reviewId);
    }

    private boolean noAuthReview(Long reviewId, UserInfoDto user) {
        if(user == null) return true;
        Review findReview = reviewService.getReviewByIdWithFetchJoin(reviewId);
        return !findReview.getUser().getId().equals(user.getId());
    }

    private String alertMessage(String redirectPathName, String errorMsg, Model model) {
        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("url", redirectPathName);
        return "fragments/alert";
    }
}
