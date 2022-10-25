package PorkCutlet.master.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import PorkCutlet.master.domain.Address;
import PorkCutlet.master.domain.ForkCutlet;
import PorkCutlet.master.domain.ForkCutletType;
import PorkCutlet.master.domain.Image;
import PorkCutlet.master.domain.Rating;
import PorkCutlet.master.domain.RatingInfo;
import PorkCutlet.master.domain.Restaurant;
import PorkCutlet.master.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewForm {
	@NotEmpty(message = "가게 이름을 입력해주세요.")
	private String restaurantName;

	@NotEmpty(message = "메뉴 이름을 입력해주세요.")
	private String forkCutletName;

	@NotNull(message = "돈가스 타입을 정해주세요.")
	private Integer forkCutletType;

	@NotEmpty(message = "주소를 입력해주세요.")
	private String roadAddress;

	@Builder.Default
	private String detailAddress = "";

	@NotEmpty(message = "한줄평을 입력해주세요.")
	private String oneSentence;

	@NotEmpty(message = "상세 리뷰를 입력해주세요.")
	private String content;

	@Max(value = 5, message = "최대 평점은 5입니다.")
	@Min(value = 1, message = "최소 평점은 1입니다.")
	@NotNull(message = "평점을 입력해주세요.")
	private Integer texture;

	@Max(value = 5, message = "최대 평점은 5입니다.")
	@Min(value = 1, message = "최소 평점은 1입니다.")
	@NotNull(message = "평점을 입력해주세요.")
	private Integer sauce;

	@Max(value = 5, message = "최대 평점은 5입니다.")
	@Min(value = 1, message = "최소 평점은 1입니다.")
	@NotNull(message = "평점을 입력해주세요.")
	private Integer side;

	@Max(value = 5, message = "최대 평점은 5입니다.")
	@Min(value = 1, message = "최소 평점은 1입니다.")
	@NotNull(message = "평점을 입력해주세요.")
	private Integer accessibility;

	private List<String> preImages;

	private List<String> deleteImages;

	private List<MultipartFile> imageFiles;

	public static ReviewForm from(Review review) {
		return new ReviewForm(review.getRestaurant().getName(), review.getRestaurant().getForkCutlet().getName(),
			review.getRestaurant().getForkCutlet().getForkCutletType().getValue(),
			review.getRestaurant().getAddress().getRoadAddress(),
			review.getRestaurant().getAddress().getDetailAddress(), review.getOneSentence(), review.getContent(),
			review.getRatingInfo().getTexture().getValue(), review.getRatingInfo().getSauce().getValue(),
			review.getRatingInfo().getSide().getValue(), review.getRatingInfo().getAccessibility().getValue(),
			review.getImages().stream().map(Image::getStoreImageName).collect(Collectors.toList()), new ArrayList<>(),
			null);
	}

	public Address makeAddress() {
		return new Address(roadAddress, detailAddress);
	}

	public ForkCutlet makeForkCutlet() {
		return new ForkCutlet(forkCutletName, makeForkCutletType());
	}

	public ForkCutletType makeForkCutletType() {
		return ForkCutletType.values()[forkCutletType];
	}

	public Restaurant makeRestaurant() {
		return new Restaurant(restaurantName, makeAddress(), makeForkCutlet());
	}

	public RatingInfo makeRatingInfo() {
		int[] ratings = new int[] {texture, sauce, side, accessibility};
		Rating[] values = Rating.values();
		return new RatingInfo(values[ratings[0]], values[ratings[1]], values[ratings[2]], values[ratings[3]]);
	}
}
