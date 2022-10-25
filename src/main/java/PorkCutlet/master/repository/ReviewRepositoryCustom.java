package PorkCutlet.master.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.Tuple;

import PorkCutlet.master.domain.ForkCutletType;
import PorkCutlet.master.domain.Review;

public interface ReviewRepositoryCustom {
	List<Review> findReviewsWithFetchJoin();

	Review findReviewByIdWithFetchJoin(Long id);

	List<Review> findTop6Reviews();

	Review findMostRecentReview();

	List<Review> findReviewsPageWithFetchJoin(Pageable pageable);

	List<Tuple> findForkCutletTypeRateByUserLikes(Long userId);

	List<Review> findReviewsByForkCutletType(ForkCutletType forkCutletType);
}
