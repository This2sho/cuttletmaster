package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Review;

import java.util.List;

public interface LikeRepositoryCustom {
    List<Review> findReviewsByUserIdWithFetchJoin(Long userId);
}
