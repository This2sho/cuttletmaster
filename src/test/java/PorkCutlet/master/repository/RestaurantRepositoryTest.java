package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Address;
import PorkCutlet.master.domain.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    public void RestaurantCreateTest() {
        //given
        Address address = new Address("a city", "b street", "123");
        Restaurant restaurant = new Restaurant("cocoa", address);
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
    public void RestaurantRepositoryQueryDslTest(){
        //given
        Address address = new Address("a city", "b street", "123");
        Restaurant restaurant = new Restaurant("cocoa", address);
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
    public void findByNameTest(){
        //given
        Address address = new Address("a city", "b street", "123");
        Restaurant restaurant = new Restaurant("cocoa", address);
        restaurantRepository.save(restaurant);
        //when
        Restaurant find = restaurantRepository.findByName("cocoa").get();
        //then
        assertThat(restaurant).isEqualTo(find);
        assertThat(restaurant.getId()).isEqualTo(find.getId());
        assertThat(restaurant.getName()).isEqualTo(find.getName());
        assertThat(restaurant.getAddress()).isEqualTo(find.getAddress());
    }

}