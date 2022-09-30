package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countAllByReviewId(Long reviewId);

    Like findLikeByUserIdAndReviewId(Long userId, Long reviewId);

    boolean existsLikeByUserIdAndReviewId(Long userId, Long reviewId);
}