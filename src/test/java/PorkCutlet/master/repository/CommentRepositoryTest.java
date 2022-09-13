package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Comment;
import PorkCutlet.master.domain.Review;
import PorkCutlet.master.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void CommentRepositoryBasicTest() {
        //given
        User user1 = userRepository.findByLoginId("user1").orElseThrow();
        List<Review> all = reviewRepository.findAll();
        Comment comment = new Comment(user1, all.get(0));
        commentRepository.save(comment);
        //when
        Comment find = commentRepository.findById(comment.getId()).orElseThrow();
        //then
        assertThat(comment).isEqualTo(find);
    }
}