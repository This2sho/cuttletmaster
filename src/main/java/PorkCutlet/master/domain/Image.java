package PorkCutlet.master.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
