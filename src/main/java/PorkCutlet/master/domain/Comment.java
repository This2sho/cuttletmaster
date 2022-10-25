package PorkCutlet.master.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	private String content;

	@Builder
	public Comment(User user, Review review, String content) {
		this.user = user;
		this.review = review;
		this.content = content;
	}

	public void updateContent(String content) {
		this.content = content;
	}

}
