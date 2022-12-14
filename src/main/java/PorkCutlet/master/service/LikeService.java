package PorkCutlet.master.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PorkCutlet.master.domain.Like;
import PorkCutlet.master.domain.Review;
import PorkCutlet.master.domain.User;
import PorkCutlet.master.repository.LikeRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
	private final LikeRepository likeRepository;

	@Transactional
	public void addLike(User user, Review review) {
		Like like = new Like(user, review);
		likeRepository.save(like);
	}

	@Transactional
	public void deleteLike(Long userId, Long reviewId) {
		Like like = likeRepository.findLikeByUserIdAndReviewId(userId, reviewId);
		likeRepository.delete(like);
	}

	public Long countLikesNum(Long reviewId) {
		return likeRepository.countAllByReviewId(reviewId);
	}

	public boolean doExistLike(Long userId, Long reviewId) {
		return likeRepository.existsLikeByUserIdAndReviewId(userId, reviewId);
	}

	public List<Review> getLikeList(Long userId) {
		return likeRepository.findReviewsByUserIdWithFetchJoin(userId);
	}
}
