package PorkCutlet.master.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import PorkCutlet.master.domain.Comment;

public interface CommentRepositoryCustom {
	List<Comment> findCommentsWithFetchJoin(Pageable pageable, Long reviewId);
}
