package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Address;
import PorkCutlet.master.domain.ForkCutlet;
import PorkCutlet.master.domain.ForkCutletType;
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
class ForkCutletRepositoryTest {

    @Autowired
    ForkCutletRepository forkCutletRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    public void FoodRepositoryTest(){
        //given
        ForkCutlet forkCutlet = new ForkCutlet("바삭바삭 돈까스", ForkCutletType.KOREAN_STYLE);
        Restaurant restaurant = new Restaurant("은하수 식당", new Address("부산", "부산 대학로"), forkCutlet);
        restaurantRepository.save(restaurant);

        //when
        ForkCutlet find = forkCutletRepository.findById(forkCutlet.getId()).orElseThrow();
        //then
        assertThat(forkCutlet).isEqualTo(find);
    }

}