package PorkCutlet.master.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import PorkCutlet.master.domain.Comment;
import PorkCutlet.master.domain.Review;
import PorkCutlet.master.domain.User;

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
		Comment comment = new Comment(user1, all.get(0), "hi basic content");
		commentRepository.save(comment);
		//when
		Comment find = commentRepository.findById(comment.getId()).orElseThrow();
		//then
		assertThat(comment).isEqualTo(find);
	}

	@BeforeEach
	public void init() {
		User user1 = userRepository.findByLoginId("user1").orElseThrow();
		List<Review> all = reviewRepository.findAll();
		for (int i = 0; i < 4; i++) {
			Comment comment = new Comment(user1, all.get(0), "hi basic content" + i);
			commentRepository.save(comment);
		}
		commentRepository.findAll();
		System.out.println("===save comment===");
	}

	@Test
	public void 댓글_수정_테스트() throws Exception {
		//given
		Comment comment = commentRepository.findAll().get(0);
		assertThat(commentRepository.findById(comment.getId()).get().getContent()).isEqualTo("hi basic content");
		//when
		comment.updateContent("hello");
		//then
		assertThat(commentRepository.findById(comment.getId()).get().getContent()).isEqualTo("hello");
	}

	@Test
	public void 댓글_카운트_테스트() throws Exception {
		//given
		Review review = reviewRepository.findAll().get(0);
		//when

		//then
		Long count = commentRepository.countByReview_Id(review.getId());
		assertThat(count).isEqualTo(4);
	}
}
