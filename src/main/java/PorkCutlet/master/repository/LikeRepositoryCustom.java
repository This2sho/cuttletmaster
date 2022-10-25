package PorkCutlet.master.repository;

import java.util.List;

import PorkCutlet.master.domain.Review;

public interface LikeRepositoryCustom {
	List<Review> findReviewsByUserIdWithFetchJoin(Long userId);
}
