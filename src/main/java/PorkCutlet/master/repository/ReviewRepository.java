package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends ReviewRepositoryCustom, JpaRepository<Review, Long> {
    Optional<Review> findByRestaurant_Name(String restaurantName);
}