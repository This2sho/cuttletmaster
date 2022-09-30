package PorkCutlet.master.controller;

import PorkCutlet.master.controller.dto.HomeReviewDto;
import PorkCutlet.master.controller.dto.UserInfoDto;
import PorkCutlet.master.controller.login.Login;
import PorkCutlet.master.domain.Address;
import PorkCutlet.master.domain.Image;
import PorkCutlet.master.domain.Restaurant;
import PorkCutlet.master.domain.Review;
import PorkCutlet.master.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final ReviewService reviewService;

    @GetMapping("/")
    public String home(@Login UserInfoDto user, Model model) {
        Review mostRecentReview = reviewService.getMostRecentReview();
        model.addAttribute("mostRecentReview", reviewToHomeReviewDto(mostRecentReview));

        List<HomeReviewDto> top6Reviews = reviewService.getTop6Reviews().stream()
                .map(this::reviewToHomeReviewDto).collect(Collectors.toList());
        model.addAttribute("top6Reviews", top6Reviews);

        /**
         *
         * 현재 추천 미구현으로 null 넣어놓음 구현 후 바꿔야 함
         */
        HomeReviewDto recommendReview = reviewToHomeReviewDto(reviewService.recommend(null));
        model.addAttribute("recommendReview", recommendReview);
        return "home";
    }

    @PostMapping("/recommend")
    public String recommend(@Login UserInfoDto user, Model model) {
        HomeReviewDto recommendReview = reviewToHomeReviewDto(reviewService.recommend(null));
        model.addAttribute("recommendReview", recommendReview);
        return "fragments/recommend";
    }

    public HomeReviewDto reviewToHomeReviewDto(Review review) {
        Restaurant restaurant = review.getRestaurant();
        Address address = restaurant.getAddress();
        List<Image> images = review.getImages();

        String restaurantName = restaurant.getName();
        String forkCutletName = restaurant.getForkCutlet().getName();
        String roadAddress = address.getRoadAddress();
        String detailAddress = address.getDetailAddress();

        List<String> storeFileNames = new ArrayList<>();
        for (Image image : images) {
            storeFileNames.add(image.getStoreImageName());
        }
        return new HomeReviewDto(restaurantName, forkCutletName, roadAddress, detailAddress, storeFileNames);
    }


}
