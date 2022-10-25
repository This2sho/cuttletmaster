package PorkCutlet.master.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "likes")
public class Like {
	@Id
	@GeneratedValue
	@Column(name = "like_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_Id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	public Like(User user, Review review) {
		this.user = user;
		this.review = review;
	}

	//연관관계 수정 단방향으로 하고 필요하면 양방향으로 바꾸자
}
