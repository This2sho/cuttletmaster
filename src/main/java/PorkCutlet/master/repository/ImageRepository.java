package PorkCutlet.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PorkCutlet.master.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findByUploadImageName(String imageName);

	Optional<Image> findByStoreImageName(String storeImageName);

	void deleteByStoreImageName(String storeImageName);

	List<Image> findByReviewId(Long reviewId);
}
