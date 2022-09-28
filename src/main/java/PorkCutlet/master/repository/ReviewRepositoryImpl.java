package PorkCutlet.master.repository;

import PorkCutlet.master.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static PorkCutlet.master.domain.QRestaurant.*;
import static PorkCutlet.master.domain.QReview.*;
import static PorkCutlet.master.domain.QUser.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findReviewsFetchJoin() {
      return queryFactory.selectFrom(review)
                .join(review.restaurant, restaurant).fetchJoin()
                .join(review.user, user).fetchJoin()
                .fetch();
    }

    @Override
    public Page<Review> findReviewsPageWithFetchJoin(Pageable pageable) {
        long pageNum = pageable.getOffset() == 0 ? 0 : pageable.getOffset() - pageable.getPageSize();
        List<Review> content = queryFactory.selectFrom(review)
                .join(review.restaurant, restaurant).fetchJoin()
                .join(review.user, user).fetchJoin()
                .orderBy(review.createdDate.desc())
                .offset(pageNum)
                .limit(pageable.getPageSize())
                .fetch();
        int total = content.size();
        return new PageImpl<> (content, pageable, total);
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