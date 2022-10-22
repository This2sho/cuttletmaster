package PorkCutlet.master.repository;

import PorkCutlet.master.domain.*;
import PorkCutlet.master.service.ReviewService;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static PorkCutlet.master.domain.QForkCutlet.forkCutlet;
import static PorkCutlet.master.domain.QLike.like;
import static PorkCutlet.master.domain.QRestaurant.restaurant;
import static PorkCutlet.master.domain.QReview.review;
import static PorkCutlet.master.domain.QUser.user;
import static PorkCutlet.master.domain.Rating.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    ReviewService reviewService;

    @Test
    public void ReviewRepositoryTest() {
        //given
        Image image = new Image("upload", "store");
        List<Image> images = List.of(image);
        Review review = new Review(userRepository.findByLoginId("user1").get(), restaurantRepository.findByName("식당1").get(),images, "it's sososo gooood","마싯쿤", new RatingInfo(FIVE, THREE, FOUR, ONE));
        reviewRepository.save(review);
        //when
        Review find = reviewRepository.findById(review.getId()).orElseThrow();
        //then
        assertThat(review).isEqualTo(find);
        System.out.println(find.getRatingInfo());
        System.out.println(find.getRestaurant());

    }

    @Test
    public void 리뷰_레스토랑_한방쿼리_테스트() throws Exception {
        //given
        List<Review> reviewsFetchJoin = reviewRepository.findReviewsWithFetchJoin();
        Review randomReview = reviewsFetchJoin.stream().findAny().orElseThrow();
        //when
        System.out.println("reviewsFetchJoin.size() = " + reviewsFetchJoin.size());
        System.out.println(randomReview.getRestaurant().getName());
        System.out.println(randomReview.getImages().get(0).getStoreImageName());
        System.out.println(randomReview.getRestaurant().getForkCutlet().getName());
        //then
    }

    @Test
    public void 제일_높은_리뷰_6개_찾기() throws Exception {
        //given
        List<Review> reviews = reviewRepository.findTop6Reviews();
        //when
        for (Review review : reviews) {
            System.out.println("review = " + review.getRestaurant().getName());
        }
        //then

    }

    @Test
    public void 제일_최신_리뷰찾기() throws Exception {
        //given
        Review mostRecentReview = reviewRepository.findMostRecentReview();
        //when
        System.out.println("mostRecentReview = " + mostRecentReview.getRestaurant().getName());
        System.out.println("created = " + mostRecentReview.getCreatedDate());
        //then

    }

    @Test
    public void 더좋아하는돈가스타입() throws Exception {
        //given
        List<User> users = userRepository.findAll();
        User user0 = users.get(0);
        List<Review> reviews = reviewRepository.findAll();
        reviews.get(0).getRestaurant().getForkCutlet().update("aaa", ForkCutletType.JAPANESE_STYLE);
        for (int i = 0; i < 3; i++) {
            Like like1 = new Like(user0, reviews.get(i));
            likeRepository.save(like1);
        }

        Long userId = user0.getId();

        List<Tuple> fetch = queryFactory.select(forkCutlet.forkCutletType, forkCutlet.count())
                .from(like)
                .join(like.review, review)
                .join(like.user, user)
                .join(review.restaurant, restaurant)
                .join(restaurant.forkCutlet, forkCutlet)
                .where(user.id.eq(userId))
                .groupBy(forkCutlet.forkCutletType)
                .fetch();

        for (Tuple tuple : fetch) {
            System.out.print(tuple.get(0, ForkCutletType.class) + " : ");
            System.out.println(tuple.get(1, Long.class));
        }
    }

    @Test
    public void 원하는_돈가스타입_리뷰_가져오기_테스트() throws Exception {
        //given
        List<User> users = userRepository.findAll();
        User user0 = users.get(0);
        List<Review> reviews = reviewRepository.findAll();
        reviews.get(0).getRestaurant().getForkCutlet().update("aaa", ForkCutletType.JAPANESE_STYLE);
        for (int i = 0; i < 3; i++) {
            Like like1 = new Like(user0, reviews.get(i));
            likeRepository.save(like1);
        }

        ForkCutletType forkCutletType = ForkCutletType.JAPANESE_STYLE;

        List<Review> fetch = queryFactory.selectFrom(review)
                .join(review.restaurant, restaurant).fetchJoin()
                .join(review.user, user).fetchJoin()
                .where(restaurant.forkCutlet.forkCutletType.eq(forkCutletType))
                .fetch();

        for (Review review : fetch) {
            System.out.println(review.getRestaurant().getName());
            System.out.println(review.getRestaurant().getForkCutlet().getForkCutletType());
        }
    }

    @Test
    public void 돈가스_추천() throws Exception {
        List<User> users = userRepository.findAll();
        User user0 = users.get(0);
        List<Review> reviews = reviewRepository.findAll();
        reviews.get(0).getRestaurant().getForkCutlet().update("aaa", ForkCutletType.JAPANESE_STYLE);

        for (int i = 0; i < 3; i++) {
            Like like1 = new Like(user0, reviews.get(i));
            likeRepository.save(like1);
        }

        Long userId = user0.getId();

        List<Review> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Review recommendReview = reviewService.getRecommendReview(userId);
            result.add(recommendReview);
        }

        int KOREAN_STYLE = 0;
        int JAPANESE_STYLE = 0;

        for (Review review : result) {
            if (review.getRestaurant().getForkCutlet().getForkCutletType() == ForkCutletType.KOREAN_STYLE) {
                KOREAN_STYLE++;
            }else{
                JAPANESE_STYLE++;
            }
        }

        System.out.println("KOREAN_STYLE : " + KOREAN_STYLE + ", JAPANESE_STYLE : " + JAPANESE_STYLE);
    }
}