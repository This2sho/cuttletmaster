package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}