package PorkCutlet.master.controller;

import PorkCutlet.master.controller.dto.HomeReviewDto;
import PorkCutlet.master.controller.dto.UserInfoDto;
import PorkCutlet.master.controller.login.Login;
import PorkCutlet.master.domain.Review;
import PorkCutlet.master.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("mostRecentReview", HomeReviewDto.from(mostRecentReview));

        List<HomeReviewDto> top6Reviews = reviewService.getTop6Reviews().stream()
                .map(HomeReviewDto::from).collect(Collectors.toList());
        model.addAttribute("top6Reviews", top6Reviews);

        /**
         *
         * 현재 추천 미구현으로 null 넣어놓음 구현 후 바꿔야 함
         */
        HomeReviewDto recommendReview = HomeReviewDto.from(reviewService.recommend(null));
        model.addAttribute("recommendReview", recommendReview);
        return "home";
    }

    @PostMapping("/recommend")
    public String recommend(@Login UserInfoDto user, Model model) {
        HomeReviewDto recommendReview = HomeReviewDto.from(reviewService.recommend(null));
        model.addAttribute("recommendReview", recommendReview);
        return "fragments/recommend";
    }
}
