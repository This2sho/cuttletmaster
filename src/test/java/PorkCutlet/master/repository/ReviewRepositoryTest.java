package PorkCutlet.master.repository;

import PorkCutlet.master.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static PorkCutlet.master.domain.Rating.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    public void ReviewRepositoryTest() {
        //given
        Restaurant restaurant = restaurantRepository.findByName("식당1").orElseThrow();
        Review review = new Review("마싯쿤", restaurant, new RatingInfo(Five, THREE, FOUR, ONE));
        reviewRepository.save(review);
        //when
        Review find = reviewRepository.findById(review.getId()).orElseThrow();
        //then
        assertThat(review).isEqualTo(find);
        System.out.println(find.getRatingInfo());
        System.out.println(find.getRestaurant());

    }

//    @Test
//    public void ProxyTest() {
//        //given
//        Review review = reviewRepository.findByContent("맛있다1").orElseThrow();
//        //when
//        System.out.println(review.getRestaurant().getClass());
//        //then
//
////        Restaurant restaurant = restaurantRepository.findByName("식당1").orElseThrow();
////        System.out.println(restaurant);
//    }
}