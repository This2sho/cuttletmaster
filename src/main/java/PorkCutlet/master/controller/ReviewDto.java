package PorkCutlet.master.controller;

import PorkCutlet.master.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewDto {
    @NotEmpty(message = "가게 이름을 입력해주세요.")
    private String restaurantName;

    @NotEmpty(message = "메뉴 이름을 입력해주세요.")
    private String forkCutletName;

    @NotNull(message = "돈가스 타입을 정해주세요.")
    private int forkCutletType;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String roadAddress;
    private String detailAddress;
    private String postcode;

    private List<MultipartFile> imageFiles;

    @NotEmpty(message = "리뷰를 입력해주세요.")
    private String content;

    @Max(value = 5, message = "최대 평점은 5입니다.")
    @Min(value = 1, message = "최소 평점은 1입니다.")
    private int texture;

    @Max(value = 5, message = "최대 평점은 5입니다.")
    @Min(value = 1, message = "최소 평점은 1입니다.")
    private int sauce;

    @Max(value = 5, message = "최대 평점은 5입니다.")
    @Min(value = 1, message = "최소 평점은 1입니다.")
    private int side;

    @Max(value = 5, message = "최대 평점은 5입니다.")
    @Min(value = 1, message = "최소 평점은 1입니다.")
    private int accessibility;


//    public static ReviewDto from(Review review) {
//        String restaurantName;
//        String forkCutletName;
//        int forkCutletType;
//        String roadAddress;
//        String detailAddress;
//        String postcode;
//        List<MultipartFile> imageFiles;
//        String content;
//        int texture;
//        int sauce;
//        int side;
//        int accessibility;
//        return new ReviewDto(restaurantName, forkCutletName, forkCutletType, roadAddress, detailAddress, postcode,
//                imageFiles, content, texture, sauce, side, accessibility);
//    }

    public Address makeAddress() {
        return new Address(roadAddress, detailAddress, postcode);
    }

    public ForkCutlet makeForkCutlet() {
        return new ForkCutlet(forkCutletName, ForkCutletType.values()[forkCutletType]);
    }

    public Restaurant makeRestaurant() {
        return new Restaurant(restaurantName, makeAddress(), makeForkCutlet());
    }

    public RatingInfo makeRatingInfo() {
        int[] ratings = new int[] {texture, sauce, side, accessibility};
        Rating[] values = Rating.values();
        return new RatingInfo(values[ratings[0]-1], values[ratings[1]-1], values[ratings[2]-1], values[ratings[3]-1]);
    }
}
