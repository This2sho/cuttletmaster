package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Address;
import PorkCutlet.master.domain.Food;
import PorkCutlet.master.domain.FoodType;
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
class FoodRepositoryTest {

    @Autowired
    FoodRepository foodRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    public void FoodRepositoryTest(){
        //given
        Restaurant restaurant = new Restaurant("은하수 식당", new Address("부산", "부산 대학로", "147-1"));
        restaurantRepository.save(restaurant);
        Food food = new Food("바삭바삭 돈까스", 7000, FoodType.KOREAN_STYLE, restaurant);
        foodRepository.save(food);

        //when
        Food find = foodRepository.findById(food.getId()).orElseThrow();
        //then
        assertThat(food).isEqualTo(find);
    }

}