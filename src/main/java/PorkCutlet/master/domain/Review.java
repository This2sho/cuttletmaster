package PorkCutlet.master.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "review_id")
	private Long id;

	private String content;

	private String oneSentence;

	@Embedded
	private RatingInfo ratingInfo;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images = new ArrayList<>();

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Like> likes = new ArrayList<>();

	public Review(User user, Restaurant restaurant, List<Image> images, String content, String oneSentence,
		RatingInfo ratingInfo) {
		this.user = user;
		this.restaurant = restaurant;
		this.content = content;
		this.oneSentence = oneSentence;
		setImage(images);
		this.ratingInfo = ratingInfo;
	}

	public void setImage(List<Image> images) {
		for (Image image : images) {
			if (this.images.contains(image))
				continue;
			this.images.add(image);
			image.setReview(this);
		}
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
	}

	public void update(String forkCutletName, ForkCutletType forkCutletType, String restaurantName, Address address,
		String content, String oneSentence, RatingInfo ratingInfo, List<Image> images) {
		this.restaurant.update(forkCutletName, forkCutletType, restaurantName, address);
		this.content = content;
		this.oneSentence = oneSentence;
		this.ratingInfo = ratingInfo;
		setImage(images);
	}
}
