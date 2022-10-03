package PorkCutlet.master.repository;

import PorkCutlet.master.domain.QUser;
import PorkCutlet.master.domain.Review;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static PorkCutlet.master.domain.QLike.*;
import static PorkCutlet.master.domain.QRestaurant.restaurant;
import static PorkCutlet.master.domain.QReview.*;
import static PorkCutlet.master.domain.QUser.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeRepositoryImpl implements LikeRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Review> findReviewsByUserIdWithFetchJoin(Long userId) {
        return queryFactory.select(review)
                .from(like)
                .join(like.review, review)
                .join(review.restaurant, restaurant).fetchJoin()
                .join(review.user, user).fetchJoin()
                .join(like.user, user)
                .where(user.id.eq(userId))
                .fetch();
    }
}