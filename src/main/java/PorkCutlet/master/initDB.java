package PorkCutlet.master;

import PorkCutlet.master.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import static PorkCutlet.master.domain.Rating.*;

@Component
@RequiredArgsConstructor
public class initDB {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            char[] 성 = {'김', '박', '손'};
            for (int i = 0; i < 3; i++) {
                em.persist(new User("user" + i, "1234", 성[i] + "민수"));
            }

            Rating[] 별점 = {ONE, TWO, THREE, FOUR, Five};
            for (int i = 1; i < 5; i++) {
                Restaurant restaurant = new Restaurant("식당" + i, new Address("부산", "부산 대학로", "147-" + i));
                em.persist(restaurant);
                em.persist(new Review("맛있다"+i, restaurant, new RatingInfo(별점[i], 별점[i], 별점[i], 별점[i])));
            }
        }
    }

}
