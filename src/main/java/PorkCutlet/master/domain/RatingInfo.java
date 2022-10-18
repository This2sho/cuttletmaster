package PorkCutlet.master.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RatingInfo {
    private Rating texture;
    private Rating sauce;
    private Rating side;
    private Rating accessibility;
    private double overall;

    @Builder
    public RatingInfo(Rating texture, Rating sauce, Rating side, Rating accessibility) {
        this.texture = texture;
        this.sauce = sauce;
        this.side = side;
        this.accessibility = accessibility;
        this.overall = (texture.getValue() + sauce.getValue() + side.getValue() + accessibility.getValue()) * 10 / 40.0;
    }
}
