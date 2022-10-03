package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends CommentRepositoryCustom, JpaRepository<Comment, Long> {
    Long countByReview_Id(Long reviewId);
}