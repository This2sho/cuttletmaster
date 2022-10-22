package PorkCutlet.master.controller.dto;

import PorkCutlet.master.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DetailReviewDto {
    private String restaurantName;
    private String forkCutletName;
    private String forkCutletType;

    private String createdDate;
    private String userNickName;

    private String oneSentence;
    private String content;

    private List<String> storeImageNames;
    private Map<String, Integer> ratingInfo;
    private Double overallRating;

    public static DetailReviewDto from(Review review) {
        return new DetailReviewDto(review.getRestaurant().getName(), review.getRestaurant().getForkCutlet().getName(), review.getRestaurant().getForkCutlet().getForkCutletType().getName(),
                review.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), review.getUser().getNickName(),
                review.getOneSentence(), review.getContent(), makeStoreImageNames(review), makeRatingMap(review), review.getRatingInfo().getOverall());
    }

    private static List<String> makeStoreImageNames(Review review) {
        List<String> imageNames = new ArrayList<>();
        review.getImages().stream().forEach(o -> imageNames.add(o.getStoreImageName()));
        return imageNames;
    }

    private static Map<String, Integer> makeRatingMap(Review review) {
        Map<String, Integer> ratingInfo = new HashMap<>();
        ratingInfo.put("texture", review.getRatingInfo().getTexture().getValue());
        ratingInfo.put("sauce",review.getRatingInfo().getSauce().getValue());
        ratingInfo.put("side",review.getRatingInfo().getSide().getValue());
        ratingInfo.put("accessibility",review.getRatingInfo().getAccessibility().getValue());
        return ratingInfo;
    }
}
