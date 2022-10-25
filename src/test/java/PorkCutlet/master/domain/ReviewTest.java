package PorkCutlet.master.domain;

import static PorkCutlet.master.domain.Rating.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import PorkCutlet.master.repository.ImageRepository;
import PorkCutlet.master.repository.RestaurantRepository;
import PorkCutlet.master.repository.ReviewRepository;
import PorkCutlet.master.repository.UserRepository;

@SpringBootTest
@Transactional
class ReviewTest {
	static final Rating[] 별점 = {ONE, TWO, THREE, FOUR, FIVE};
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RestaurantRepository restaurantRepository;

	@Test
	public void ReviewImageTest() throws Exception {
		//given
		Image image = new Image("upload", "store");
		List<Image> images = List.of(image);
		Review review = new Review(userRepository.findByLoginId("user1").get(),
			restaurantRepository.findByName("식당1").get(), images, "it's sososo gooood", "good",
			new RatingInfo(별점[2], 별점[2], 별점[2], 별점[2]));
		//when
		Review imageReview = image.getReview();
		Image reviewImage = review.getImages().get(0);
		//then
		assertThat(imageReview).isEqualTo(review);
		assertThat(reviewImage).isEqualTo(image);
	}

	@Test
	@Rollback(value = false)
	/**
	 * image와 리뷰는 cascade all 관계라 image를 persist하지 않아도 됨
	 * **/
	public void 이미지DB생성테스트() throws Exception {
		//given
		Image image = new Image("upload", "store");
		List<Image> images = List.of(image);
		Review review = new Review(userRepository.findByLoginId("user1").get(),
			restaurantRepository.findByName("식당1").get(), images, "it's sososo gooood", "good",
			new RatingInfo(별점[2], 별점[2], 별점[2], 별점[2]));
		reviewRepository.save(review);
		//when
		Review imageReview = image.getReview();
		Image reviewImage = review.getImages().get(0);
		//then
		assertThat(imageReview).isEqualTo(review);
		assertThat(reviewImage).isEqualTo(image);
	}

	@Test
	/**
	 * 리뷰를 삭제했을 때 이미지는 삭제되야되지만,
	 * 이미지를 삭제했을 때 리뷰는 삭제되면 안됨
	 * **/
	public void 고아객체_이미지_삭제테스트() throws Exception {
		//given
		Image image = new Image("upload", "store");
		List<Image> images = List.of(image);
		Review review = new Review(userRepository.findByLoginId("user1").get(),
			restaurantRepository.findByName("식당1").get(), images, "it's sososo gooood", "good",
			new RatingInfo(별점[2], 별점[2], 별점[2], 별점[2]));
		reviewRepository.save(review);
		//when
		imageRepository.delete(image);
		//then
		Optional<Review> findReview = reviewRepository.findById(review.getId());
		assertThat(review).isEqualTo(findReview.get());
	}

	@Test
	/**
	 * 리뷰를 삭제했을 때 이미지는 삭제되야되지만,
	 * 이미지를 삭제했을 때 리뷰는 삭제되면 안됨
	 * **/
	public void 고아객체_리뷰_삭제테스트() throws Exception {
		//given
		Image image = new Image("upload", "store");
		List<Image> images = List.of(image);
		Review review = new Review(userRepository.findByLoginId("user1").get(),
			restaurantRepository.findByName("식당1").get(), images, "it's sososo gooood", "good",
			new RatingInfo(별점[2], 별점[2], 별점[2], 별점[2]));
		reviewRepository.save(review);
		//when
		reviewRepository.delete(review);
		//then
		Optional<Image> findImage = imageRepository.findById(image.getId());
		assertThat(findImage).isEmpty();
	}

	@Test
	public void 지연로딩_리뷰_프록시_테스트() throws Exception {
		//given
		Review findReview = reviewRepository.findByRestaurant_Name("식당1").get();
		// select 쿼리 2개 나감
	}

	@Test
	public void 지연로딩_이미지_프록시_테스트() throws Exception {
		//given
		Image image = imageRepository.findByUploadImageName("upload1").get();
		//select 쿼리 1개 나감.
		assertThat(image.getReview().getClass()).isNotEqualTo(Review.class);
	}

}
