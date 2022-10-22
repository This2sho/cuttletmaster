package PorkCutlet.master.repository;

import PorkCutlet.master.domain.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static PorkCutlet.master.domain.QForkCutlet.forkCutlet;
import static PorkCutlet.master.domain.QLike.like;
import static PorkCutlet.master.domain.QRestaurant.*;
import static PorkCutlet.master.domain.QReview.*;
import static PorkCutlet.master.domain.QUser.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Review findReviewByIdWithFetchJoin(Long id) {
        return queryFactory.selectFrom(review)
                .where(review.id.eq(id))
                .join(review.restaurant, restaurant).fetchJoin()
                .join(review.user, user).fetchJoin()
                .fetchOne();
    }

    @Override
    public List<Review> findReviewsWithFetchJoin() {
      return queryFactory.selectFrom(review)
                .join(review.restaurant, restaurant).fetchJoin()
                .join(review.user, user).fetchJoin()
                .fetch();
    }

    @Override
    public List<Review> findReviewsPageWithFetchJoin(Pageable pageable) {
        long pageNum = pageable.getOffset() == 0 ? 0 : pageable.getOffset() - pageable.getPageSize();
        return queryFactory.selectFrom(review)
                .join(review.restaurant, restaurant).fetchJoin()
                .join(review.user, user).fetchJoin()
                .orderBy(review.createdDate.desc())
                .offset(pageNum)
                .limit(pageable.getPageSize())
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

    @Override
    public List<Tuple> findForkCutletTypeRateByUserLikes(Long userId) {
        return queryFactory.select(forkCutlet.forkCutletType, forkCutlet.count())
                .from(like)
                .join(like.review, review)
                .join(like.user, user)
                .join(review.restaurant, restaurant)
                .join(restaurant.forkCutlet, forkCutlet)
                .where(user.id.eq(userId))
                .groupBy(forkCutlet.forkCutletType)
                .fetch();
    }

    @Override
    public List<Review> findReviewsByForkCutletType(ForkCutletType forkCutletType) {
        return queryFactory.selectFrom(review)
                .join(review.restaurant, restaurant).fetchJoin()
                .join(review.user, user).fetchJoin()
                .where(restaurant.forkCutlet.forkCutletType.eq(forkCutletType))
                .fetch();
    }

}
