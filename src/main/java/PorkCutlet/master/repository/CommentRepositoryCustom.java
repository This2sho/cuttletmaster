package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {
    Page<Comment> findCommentsPageWithFetchJoin(Pageable pageable, Long reviewId);
}
