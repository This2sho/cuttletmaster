package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryCustom {
    Optional<Restaurant> findByName(String name);
}
