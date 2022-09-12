package PorkCutlet.master.repository;

import PorkCutlet.master.domain.*;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    @BeforeEach
    public void beforeEach() {
        char[] 성 = {'김', '박', '손'};
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            users.add(new User(성[i] + "민수", "1234", "user" + i));
        }
        userRepository.saveAll(users);

        List<Restaurant> restaurants = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        Rating[] 별점 = {Rating.ONE, Rating.TWO, Rating.THREE, Rating.FOUR, Rating.Five};
        for (int i = 1; i < 5; i++) {
            restaurants.add(new Restaurant("식당"+i, new Address("부산", "부산 대학로", "147-" + i)));
            reviews.add(new Review("정말 맛있다", restaurants.get(i-1), new RatingInfo(별점[i], 별점[i], 별점[i], 별점[i])));
        }
        restaurantRepository.saveAll(restaurants);
        reviewRepository.saveAll(reviews);
    }

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