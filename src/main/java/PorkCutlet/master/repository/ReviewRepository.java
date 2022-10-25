package PorkCutlet.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PorkCutlet.master.domain.Review;

public interface ReviewRepository extends ReviewRepositoryCustom, JpaRepository<Review, Long> {
	Optional<Review> findByRestaurant_Name(String restaurantName);
}
