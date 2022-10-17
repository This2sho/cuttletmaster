package PorkCutlet.master.repository;

import PorkCutlet.master.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUploadImageName(String imageName);

    Optional<Image> findByStoreImageName(String storeImageName);

    void deleteByStoreImageName(String storeImageName);

    List<Image> findByReviewId(Long reviewId);
}
