package PorkCutlet.master;

import PorkCutlet.master.domain.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImageUtils {
    @Value("${file.dir}")
    private String fileDir;

    public List<Image> updateImages(List<String> deleteImages, List<MultipartFile> multipartFiles) throws IOException {
        if (deleteImages != null) {
            for (String deleteImage : deleteImages) {
                File file = new File(getFullPath(deleteImage));
                file.delete();
            }
        }
        return multipartFiles != null ? storeImages(multipartFiles) : null;
    }

    public List<Image> storeImages(List<MultipartFile> multipartFiles) throws IOException {
        List<Image> storeImageResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeImageResult.add(storeImageFile(multipartFile));
            }
        }
        return storeImageResult;
    }

    private Image storeImageFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        file.transferTo(new File(getFullPath(storeFileName)));
        return new Image(originalFilename, storeFileName);
    }

    public String getFullPath(String storeFileName) {
        return fileDir + storeFileName;
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
