package PorkCutlet.master.repository;

import PorkCutlet.master.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback
class LikeRepositoryTest {
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    public void LikeRepositoryTest() {
        //given
        List<User> users = userRepository.findAll();
        List<Review> reviews = reviewRepository.findAll();
        Like like = new Like(users.get(1), reviews.get(1));
        likeRepository.save(like);
        //when
        Like find = likeRepository.findById(like.getId()).orElseThrow();
        //then
        assertThat(like).isEqualTo(find);

    }

    @Test
    public void 좋아요_수_테스트() throws Exception {
        //given
        List<User> users = userRepository.findAll();
        List<Review> reviews = reviewRepository.findAll();
        for (int i = 0; i < 3; i++) {
            Like like = new Like(users.get(i), reviews.get(0));
            likeRepository.save(like);
        }
        //when
        Long likesNum = likeRepository.countAllByReviewId(reviews.get(0).getId());

        //then
        assertThat(likesNum).isEqualTo(3);
    }

    @Test
    public void 좋아요_삭제_테스트() throws Exception {
        //given
        List<User> users = userRepository.findAll();
        List<Review> reviews = reviewRepository.findAll();
        Like like = null;
        for (int i = 0; i < 3; i++) {
            like = new Like(users.get(i), reviews.get(0));
            likeRepository.save(like);
        }
        //when
        Long likesNum = likeRepository.countAllByReviewId(reviews.get(0).getId());
        assertThat(likesNum).isEqualTo(3);
        //then
        likeRepository.delete(like);
        likesNum = likeRepository.countAllByReviewId(reviews.get(0).getId());
        assertThat(likesNum).isEqualTo(2);
    }
}