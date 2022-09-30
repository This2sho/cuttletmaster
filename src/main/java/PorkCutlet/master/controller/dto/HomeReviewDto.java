package PorkCutlet.master.controller.dto;

import PorkCutlet.master.domain.Address;
import PorkCutlet.master.domain.Image;
import PorkCutlet.master.domain.Restaurant;
import PorkCutlet.master.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class HomeReviewDto {
    private String restaurantName;
    private String forkCutletName;
    private String roadAddress;
    private String detailAddress;
    private List<String> storeFileNames;

    public static HomeReviewDto from(Review review) {
        Restaurant restaurant = review.getRestaurant();
        Address address = restaurant.getAddress();
        List<Image> images = review.getImages();

        String restaurantName = restaurant.getName();
        String forkCutletName = restaurant.getForkCutlet().getName();
        String roadAddress = address.getRoadAddress();
        String detailAddress = address.getDetailAddress();

        List<String> storeFileNames = new ArrayList<>();
        for (Image image : images) {
            storeFileNames.add(image.getStoreImageName());
        }
        return new HomeReviewDto(restaurantName, forkCutletName, roadAddress, detailAddress, storeFileNames);
    }
}
