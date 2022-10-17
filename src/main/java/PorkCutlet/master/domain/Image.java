package PorkCutlet.master.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Image {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private String uploadImageName;
    private String storeImageName;

    @Builder
    public Image(String uploadImageName, String storeImageName) {
        this.uploadImageName = uploadImageName;
        this.storeImageName = storeImageName;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object other) {
        return this.getStoreImageName().equals(other);
    }
}
