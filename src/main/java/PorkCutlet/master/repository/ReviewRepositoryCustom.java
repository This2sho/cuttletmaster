package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ReviewRepositoryCustom {
    List<Review> findReviewsFetchJoin();

    Page<Review> findReviewsPageWithFetchJoin(Pageable pageable);

    List<Review> findTop6Reviews();

    Review findMostRecentReview();
}
