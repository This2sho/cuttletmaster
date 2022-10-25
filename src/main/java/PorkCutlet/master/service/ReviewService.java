package PorkCutlet.master.service;

import static PorkCutlet.master.controller.PageConst.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;

import PorkCutlet.master.domain.Address;
import PorkCutlet.master.domain.ForkCutletType;
import PorkCutlet.master.domain.Image;
import PorkCutlet.master.domain.RatingInfo;
import PorkCutlet.master.domain.Review;
import PorkCutlet.master.domain.UserType;
import PorkCutlet.master.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
	private final ReviewRepository reviewRepository;

	@Transactional
	public Long createReview(Review review) {
		validateReview(review); // 사용자 권한 확인
		reviewRepository.save(review);
		return review.getId();
	}

	@Transactional
	public Long updateReview(Long id, String forkCutletName, ForkCutletType forkCutletType, String restaurantName,
		Address address, String content, String oneSentence, RatingInfo ratingInfo, List<Image> images) {
		Review findReview = reviewRepository.findById(id)
			.orElseThrow(() -> new IllegalStateException("리뷰가 존재하지 않습니다."));
		findReview.update(forkCutletName, forkCutletType, restaurantName, address, content, oneSentence, ratingInfo,
			images);
		return findReview.getId();
	}

	@Transactional
	public void deleteReview(Review review) {
		reviewRepository.delete(review);
	}

	@Transactional
	public void deleteReviewById(Long reviewId) {
		reviewRepository.deleteById(reviewId);
	}

	public Optional<Review> findById(Long id) {
		return reviewRepository.findById(id);
	}

	public Review getReviewByIdWithFetchJoin(Long id) {
		return reviewRepository.findReviewByIdWithFetchJoin(id);
	}

	public Long getTotalPage() {
		Long totalSize = reviewRepository.count();
		if (totalSize == 0)
			return 1L;
		return totalSize % reviewsPageSize == 0 ? (totalSize / reviewsPageSize) : (totalSize / reviewsPageSize) + 1;
	}

	public List<Review> getPagingReview(Pageable pageable) {
		return reviewRepository.findReviewsPageWithFetchJoin(pageable);
	}

	public List<Review> getReviewAll() {
		return reviewRepository.findAll();
	}

	public Review recommend(Long userId) {
		if (userId == null) {
			return getRandomReview();
		}
		return getRecommendReview(userId);
	}

	public Review getRecommendReview(Long userId) {
		List<Tuple> userLikesType = reviewRepository.findForkCutletTypeRateByUserLikes(userId);
		if (userLikesType.size() == 0)
			return getRandomReview();

		if (userLikesType.size() == 1) {
			List<Review> LikesForkCutletTypeReview = reviewRepository.findReviewsByForkCutletType(
				userLikesType.get(0).get(0, ForkCutletType.class));
			return getRandomReview(LikesForkCutletTypeReview);
		}

		Long userLike0TypeNum = userLikesType.get(0).get(1, Long.class);
		Long userLike1TypeNum = userLikesType.get(1).get(1, Long.class);
		Long totalNum = userLike0TypeNum + userLike1TypeNum;

		List<Review> reviewsByForkCutletType0 = reviewRepository.findReviewsByForkCutletType(
			userLikesType.get(0).get(0, ForkCutletType.class));
		List<Review> reviewsByForkCutletType1 = reviewRepository.findReviewsByForkCutletType(
			userLikesType.get(1).get(0, ForkCutletType.class));
		int totalReviewNum = reviewsByForkCutletType0.size() + reviewsByForkCutletType1.size();

		int pick0TypeNum = Math.round(totalReviewNum * ((float)userLike0TypeNum / totalNum));
		int pick1TypeNum = Math.round(totalReviewNum * ((float)userLike1TypeNum / totalNum));

		int gcd = getGCD(pick0TypeNum, pick1TypeNum);

		pick0TypeNum /= gcd;
		pick1TypeNum /= gcd;

		List<Review> result = new ArrayList<>();

		for (int i = 0; i < pick0TypeNum; i++) {
			result.add(getRandomReview(reviewsByForkCutletType0));
		}
		for (int i = 0; i < pick1TypeNum; i++) {
			result.add(getRandomReview(reviewsByForkCutletType1));
		}

		return getRandomReview(result);
	}

	public Review getRandomReview() {
		List<Review> reviews = reviewRepository.findReviewsWithFetchJoin();
		int size = reviews.size();
		if (size == 0)
			return null;

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		int randomIdx = random.nextInt(size);
		return reviews.get(randomIdx);
	}

	public Review getRandomReview(List<Review> reviews) {
		int size = reviews.size();
		if (size == 0)
			return null;
		int randomIdx = (int)(Math.random() * size);
		return reviews.get(randomIdx);
	}

	public void validateReview(Review review) {
		if (review.getUser().getUserType() != UserType.ADMIN) {
			throw new IllegalStateException("리뷰 작성 권한이 없습니다.");
		}
	}

	public List<Review> getTop6Reviews() {
		List<Review> top6Reviews = reviewRepository.findTop6Reviews();
		if (top6Reviews.isEmpty())
			return new ArrayList<>();
		return top6Reviews;
	}

	public Review getMostRecentReview() {
		return reviewRepository.findMostRecentReview();
	}

	public int getGCD(int num1, int num2) {
		if (num1 < num2) {
			int tmp = num1;
			num1 = num2;
			num2 = tmp;
		}
		num1 = Math.max(num1, num2);
		num2 = Math.min(num1, num2);

		while (num2 != 0) {
			int r = num1 % num2;
			num1 = num2;
			num2 = r;
		}
		return num1;
	}
}
