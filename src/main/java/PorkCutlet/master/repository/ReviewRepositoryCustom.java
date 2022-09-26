package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Review;

import java.util.List;


public interface ReviewRepositoryCustom {
    public List<Review> findReviewsFetchJoin();

    public List<Review> findTop6Reviews();

    public Review findMostRecentReview();
}
