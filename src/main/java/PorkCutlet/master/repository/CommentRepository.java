package PorkCutlet.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import PorkCutlet.master.domain.Comment;

public interface CommentRepository extends CommentRepositoryCustom, JpaRepository<Comment, Long> {
	Long countByReview_Id(Long reviewId);
}
