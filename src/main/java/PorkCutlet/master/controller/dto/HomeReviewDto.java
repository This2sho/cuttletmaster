package PorkCutlet.master.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HomeReviewDto {
    private String restaurantName;
    private String forkCutletName;
    private String roadAddress;
    private String detailAddress;
    private List<String> storeFileNames;
}
