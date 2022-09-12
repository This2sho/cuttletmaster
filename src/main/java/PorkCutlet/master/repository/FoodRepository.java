package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}