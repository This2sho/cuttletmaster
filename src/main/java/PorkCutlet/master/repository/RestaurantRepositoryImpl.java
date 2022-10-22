package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Restaurant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static PorkCutlet.master.domain.QRestaurant.restaurant;


@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Restaurant> findRestaurantCustom() {
        return queryFactory.selectFrom(restaurant)
                .fetch();
    }
}
