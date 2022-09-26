package PorkCutlet.master.repository;

import PorkCutlet.master.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static PorkCutlet.master.domain.QImage.*;
import static PorkCutlet.master.domain.QRestaurant.*;
import static PorkCutlet.master.domain.QReview.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findReviewsFetchJoin() {
      return queryFactory.selectFrom(review)
                .join(review.restaurant, restaurant).fetchJoin()
                .join(review.images, image).fetchJoin()
                .fetch();
    }

    @Override
    public List<Review> findTop6Reviews() {
        return queryFactory.selectFrom(review)
                .orderBy(review.ratingInfo.overall.desc())
                .limit(6)
                .join(review.restaurant, restaurant).fetchJoin()
                .fetch();
    }

    @Override
    public Review findMostRecentReview() {
        return queryFactory.selectFrom(review)
                .orderBy(review.createdDate.desc())
                .limit(1)
                .join(review.restaurant, restaurant).fetchJoin()
                .fetchOne();
    }
}
