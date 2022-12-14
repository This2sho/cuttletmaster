package PorkCutlet.master;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import PorkCutlet.master.domain.Image;

@Component
public class ImageUtils {
	@Value("${file.dir}")
	private String fileDir;

	public List<Image> updateImages(List<String> deleteImages, List<MultipartFile> multipartFiles) throws IOException {
		deleteImageFilesByStoreFileName(deleteImages);
		return multipartFiles != null ? storeImages(multipartFiles) : null;
	}

	public void deleteImageFilesByStoreFileName(List<String> storeFileName) {
		if (storeFileName != null) {
			for (String deleteImage : storeFileName) {
				File file = new File(getFullPath(deleteImage));
				file.delete();
			}
		}
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
