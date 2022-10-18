package PorkCutlet.master.service;

import PorkCutlet.master.domain.*;
import PorkCutlet.master.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    public Long updateReview(Long id, String forkCutletName, ForkCutletType forkCutletType, String restaurantName, Address address, String content, String oneSentence, RatingInfo ratingInfo, List<Image> images) {
        Review findReview = reviewRepository.findById(id).orElseThrow(() -> new IllegalStateException("리뷰가 존재하지 않습니다."));
        findReview.update(forkCutletName, forkCutletType, restaurantName, address, content, oneSentence, ratingInfo, images);
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

    public Page<Review> getPagingReview(Pageable pageable) {
        return reviewRepository.findReviewsPageWithFetchJoin(pageable);
    }

    public List<Review> getReviewAll() {
        return reviewRepository.findAll();
    }

    public Review recommend(String userId) {
        if(userId == null) return getRandomReview();
        return getRecommendReview(userId);
    }

    public Review getRecommendReview(String userId) {
        List<Review> reviews = reviewRepository.findReviewsWithFetchJoin();
        /**
         * todo
         * 유저의 Like 정보를 보고 어느 타입의 돈까스를 더 좋아하는지 알아야함.
         * 그 후 더 많은 좋아요를 누른 종류의 돈가스 4개 랜덤 선택, 나머지 2개 랜덤 선택
         * 3개의 돈가스중 하나를 랜덤 선택
         */
        return null;
    }

    public Review getRandomReview() {
        List<Review> reviews = reviewRepository.findReviewsWithFetchJoin();
        int size = reviews.size();
        if(size == 0) return null;

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int randomIdx = random.nextInt(size);
        return reviews.get(randomIdx);
    }

    public void validateReview(Review review) {
        if(review.getUser().getUserType() != UserType.ADMIN){
            throw new IllegalStateException("리뷰 작성 권한이 없습니다.");
        }
    }

    public List<Review> getTop6Reviews() {
        return reviewRepository.findTop6Reviews();
    }

    public Review getMostRecentReview() {
        return reviewRepository.findMostRecentReview();
    }
}
