package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static PorkCutlet.master.domain.QComment.*;
import static PorkCutlet.master.domain.QReview.review;
import static PorkCutlet.master.domain.QUser.user;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Comment> findCommentsWithFetchJoin(Pageable pageable, Long reviewId) {
        long pageNum = pageable.getOffset() == 0 ? 0 : pageable.getOffset() - pageable.getPageSize();
        return queryFactory.selectFrom(comment)
                .join(comment.review, review).fetchJoin()
                .join(comment.user, user).fetchJoin()
                .where(comment.review.id.eq(reviewId))
                .orderBy(comment.createdDate.asc())
                .offset(pageNum)
                .limit(pageable.getPageSize())
                .fetch();
    }
}
