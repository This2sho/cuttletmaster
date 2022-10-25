package PorkCutlet.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import PorkCutlet.master.controller.auth.Login;
import PorkCutlet.master.controller.dto.UserInfoDto;
import PorkCutlet.master.service.LikeService;
import PorkCutlet.master.service.ReviewService;
import PorkCutlet.master.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LikeController {
	private final LikeService likeService;
	private final UserService userService;
	private final ReviewService reviewService;

	@PostMapping("/reviews/{reviewId}/likes")
	public String likeReview(@Login UserInfoDto user, @PathVariable Long reviewId, Model model) {
		likeService.addLike(userService.findById(user.getId()).orElseThrow(),
			reviewService.findById(reviewId).orElseThrow());
		Long likesNum = likeService.countLikesNum(reviewId);

		model.addAttribute("likes", true);
		model.addAttribute("likesNum", likesNum);
		return "fragments/likes";
	}

	@DeleteMapping("/reviews/{reviewId}/likes")
	public String disLikeReview(@Login UserInfoDto user, @PathVariable Long reviewId, Model model) {
		likeService.deleteLike(user.getId(), reviewId);
		Long likesNum = likeService.countLikesNum(reviewId);

		model.addAttribute("likes", false);
		model.addAttribute("likesNum", likesNum);
		return "fragments/likes";
	}
}
