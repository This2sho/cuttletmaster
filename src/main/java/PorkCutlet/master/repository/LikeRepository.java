package PorkCutlet.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import PorkCutlet.master.domain.Like;

public interface LikeRepository extends LikeRepositoryCustom, JpaRepository<Like, Long> {
	Long countAllByReviewId(Long reviewId);

	Like findLikeByUserIdAndReviewId(Long userId, Long reviewId);

	boolean existsLikeByUserIdAndReviewId(Long userId, Long reviewId);
}
