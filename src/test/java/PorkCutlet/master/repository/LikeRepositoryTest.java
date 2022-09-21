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
        Assertions.assertThat(like).isEqualTo(find);

    }
}