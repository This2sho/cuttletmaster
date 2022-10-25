package PorkCutlet.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PorkCutlet.master.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryCustom {
	Optional<Restaurant> findByName(String name);
}
