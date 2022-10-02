package PorkCutlet.master.service;

import PorkCutlet.master.domain.Comment;
import PorkCutlet.master.domain.Review;
import PorkCutlet.master.domain.User;
import PorkCutlet.master.repository.CommentRepository;
import PorkCutlet.master.repository.ReviewRepository;
import PorkCutlet.master.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long createComment(Long userId, Long reviewId, String content) {
        if(content.isEmpty()) return null;
        Optional<User> user = userRepository.findById(userId);
        Optional<Review> review = reviewRepository.findById(reviewId);
        Comment comment = new Comment(user.orElseThrow(), review.orElseThrow(), content);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public Long updateComment(Long userId, Long commentId, String content) throws AccessException{
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        validateAuthority(userId, comment);
        comment.updateContent(content);
        return commentId;
    }

    @Transactional
    public void deleteComment(Long userId, Long commentId) throws AccessException{
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        validateAuthority(userId, comment);
        commentRepository.delete(comment);
    }

    public Page<Comment> getCommentsWithPage(Pageable pageable, Long reviewId) {
        return commentRepository.findCommentsPageWithFetchJoin(pageable, reviewId);
    }

    public List<Comment> getComments(Long reviewId) {
        PageRequest pageRequest = PageRequest.of(0, 4);
        return commentRepository.findCommentsPageWithFetchJoin(pageRequest, reviewId).getContent();
    }



    private void validateAuthority(Long userId, Comment comment) throws AccessException {
        if(comment.getUser().getId() != userId) throw new AccessException("권한이 없습니다.");
    }
}
