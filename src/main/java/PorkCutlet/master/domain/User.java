package PorkCutlet.master.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private UserType userType = UserType.COMMON;

	private String loginId;
	private String password;
	private String nickName;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Like> likes = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@BatchSize(size = 1)
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private MasterApply masterApply;

	@Builder
	public User(String loginId, String password, String nickName) {
		this.loginId = loginId;
		this.password = password;
		this.nickName = nickName;
	}

	public void update(String password, String nickName) {
		this.password = password;
		this.nickName = nickName;
	}

	public void setAdmin() {
		this.userType = UserType.ADMIN;
	}

	public void setMasterApply(MasterApply masterApply) {
		this.masterApply = masterApply;
	}
}
