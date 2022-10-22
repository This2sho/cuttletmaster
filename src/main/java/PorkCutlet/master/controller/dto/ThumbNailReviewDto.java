package PorkCutlet.master.controller.dto;

import PorkCutlet.master.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ThumbNailReviewDto {
    private Long id;
    private String storeImageName;
    private String restaurantName;
    private String forkCutletName;
    private String forkCutletType;
    private Double overallRating;
    private String createdDate;
    private String userNickName;

    public static ThumbNailReviewDto from(Review review) {
        return new ThumbNailReviewDto(review.getId(),review.getImages().get(0).getStoreImageName(),
                review.getRestaurant().getName(), review.getRestaurant().getForkCutlet().getName(), review.getRestaurant().getForkCutlet().getForkCutletType().getName(),
                review.getRatingInfo().getOverall(), review.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), review.getUser().getNickName());
    }
}
