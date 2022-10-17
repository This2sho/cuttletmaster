package PorkCutlet.master.service;

import PorkCutlet.master.domain.Image;
import PorkCutlet.master.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public void deleteByStoreName(String storeImageName) {
        imageRepository.deleteByStoreImageName(storeImageName);
    }

    public List<Image> getImagesByReviewId(Long reviewId) {
        return imageRepository.findByReviewId(reviewId);
    }
}
