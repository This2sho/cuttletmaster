package PorkCutlet.master.controller;

import PorkCutlet.master.controller.dto.HomeReviewDto;
import PorkCutlet.master.controller.dto.UserInfoDto;
import PorkCutlet.master.controller.auth.Login;
import PorkCutlet.master.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final ReviewService reviewService;

    @GetMapping("/")
    public String home(@Login UserInfoDto user, Model model) {
        List<HomeReviewDto> top6Reviews = reviewService.getTop6Reviews().stream()
                .map(HomeReviewDto::from).collect(Collectors.toList());
        model.addAttribute("top6Reviews", top6Reviews);

        HomeReviewDto recommendReview = HomeReviewDto.from(reviewService.recommend(user != null ? user.getId() : null));
        model.addAttribute("recommendReview", recommendReview);
        return "home";
    }

    @GetMapping("/recommend")
    public String recommend(@Login UserInfoDto user, Model model) {
        HomeReviewDto recommendReview = HomeReviewDto.from(reviewService.recommend(user != null ? user.getId() : null));
        model.addAttribute("recommendReview", recommendReview);
        return "fragments/recommend";
    }
}
