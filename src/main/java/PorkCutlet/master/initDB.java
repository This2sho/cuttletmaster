package PorkCutlet.master;

import PorkCutlet.master.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

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
            List<User> users = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                users.add(new User("user" + i, "1234", 성[i] + "민수"));
                users.get(0).setAdmin();
                em.persist(users.get(i));
            }


            Rating[] 별점 = {ONE, TWO, THREE, FOUR, Five};
            for (int i = 1; i < 5; i++) {
                Restaurant restaurant = new Restaurant("식당" + i, new Address("부산", "부산 대학로", "147-" + i), new ForkCutlet("왕돈까스" + i, ForkCutletType.KOREAN_STYLE));
                em.persist(restaurant);
                Image image = new Image("upload" + i, "store" + i+".jpeg");
                if(i==4) image = new Image("upload" + i, "store" + i+".png");
                List<Image> images = new ArrayList<>();
                images.add(image);
                em.persist(new Review(users.get(i % 3), restaurant, images, "맛있다 오우 너무너무 맛있다 정말 정말 맛있다." + i,
                        "존맛탱~", new RatingInfo(별점[i], 별점[i], 별점[i], 별점[i])));
            }
        }
    }

}
