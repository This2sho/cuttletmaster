package PorkCutlet.master.repository;

import static org.assertj.core.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import PorkCutlet.master.domain.Address;
import PorkCutlet.master.domain.ForkCutlet;
import PorkCutlet.master.domain.ForkCutletType;
import PorkCutlet.master.domain.Restaurant;

@SpringBootTest
@Transactional
@Rollback
class RestaurantRepositoryTest {

	@Autowired
	RestaurantRepository restaurantRepository;

	@Test
	public void RestaurantCreateTest() {
		//given
		Address address = new Address("a city", "b street");
		Restaurant restaurant = new Restaurant("cocoa", address,
			new ForkCutlet("미니 돈까스", ForkCutletType.JAPANESE_STYLE));
		restaurantRepository.save(restaurant);
		//when
		Restaurant find = restaurantRepository.findById(restaurant.getId()).get();
		//then
		assertThat(restaurant.getId()).isEqualTo(find.getId());
		assertThat(restaurant.getName()).isEqualTo(find.getName());
		assertThat(restaurant.getAddress()).isEqualTo(find.getAddress());
		assertThat(restaurant).isEqualTo(find);
	}

	@Test
	public void RestaurantRepositoryQueryDslTest() {
		//given
		Address address = new Address("a city", "b street");
		Restaurant restaurant = new Restaurant("cocoa", address,
			new ForkCutlet("미니 돈까스", ForkCutletType.JAPANESE_STYLE));
		restaurantRepository.save(restaurant);
		//when
		Restaurant find = restaurantRepository.findRestaurantCustom().get(0);
		//then
		assertThat(restaurant).isEqualTo(find);
		assertThat(restaurant.getId()).isEqualTo(find.getId());
		assertThat(restaurant.getName()).isEqualTo(find.getName());
		assertThat(restaurant.getAddress()).isEqualTo(find.getAddress());
	}

	@Test
	public void findByNameTest() {
		//given
		Address address = new Address("a city", "b street");
		Restaurant restaurant = new Restaurant("cocoa", address,
			new ForkCutlet("미니 돈까스", ForkCutletType.JAPANESE_STYLE));
		restaurantRepository.save(restaurant);
		//when
		Restaurant find = restaurantRepository.findByName("cocoa").get();
		//then
		assertThat(restaurant).isEqualTo(find);
		assertThat(restaurant.getId()).isEqualTo(find.getId());
		assertThat(restaurant.getName()).isEqualTo(find.getName());
		assertThat(restaurant.getAddress()).isEqualTo(find.getAddress());
	}

	@Test
	public void 레스토랑_돈가스_쿼리테스트() throws Exception {
		//given
		Restaurant restaurant = restaurantRepository.findByName("식당1").orElseThrow();
		//when
		String forkcutletName = restaurant.getForkCutlet().getName();
		//then
		System.out.println("restaurant = " + restaurant.getName());
		System.out.println("forkcutletName = " + forkcutletName);
	}

}
