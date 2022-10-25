package PorkCutlet.master.repository;

import static PorkCutlet.master.domain.QComment.*;
import static PorkCutlet.master.domain.QReview.*;
import static PorkCutlet.master.domain.QUser.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import PorkCutlet.master.domain.Comment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentRepositoryImpl implements CommentRepositoryCustom {
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
