package PorkCutlet.master.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTimeEntity{
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


    void setReview(Review review) {
        this.review = review;
        review.getComments().add(this);
    }

    void setUser(User user) {
        this.user = user;
    }

    @Builder
    public Comment(User user, Review review) {
        this.user = user;
        this.review = review;
    }

    //    public Comment CreateComment(User user, Review review) {
//        Comment comment = new Comment();
//        comment.setUser(user);
//        comment.setReview(review);
//        return comment;
//    }
}
