package PorkCutlet.master.repository;

import static PorkCutlet.master.domain.QRestaurant.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import PorkCutlet.master.domain.Restaurant;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Restaurant> findRestaurantCustom() {
		return queryFactory.selectFrom(restaurant)
			.fetch();
	}
}
