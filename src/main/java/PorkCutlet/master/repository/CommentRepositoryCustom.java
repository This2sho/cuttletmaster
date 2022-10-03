package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findCommentsWithFetchJoin(Pageable pageable, Long reviewId);
}
