package PorkCutlet.master.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MasterApply {
    @Id
    @GeneratedValue
    @Column(name = "master_apply_id")
    private Long id;

    private String content;

    @OneToOne(mappedBy = "masterApply", fetch = FetchType.LAZY)
    private User user;

    public MasterApply(User user, String content) {
        setApply(user);
        this.content = content;
    }

    public void setApply(User user) {
        this.user = user;
        user.setMasterApply(this);
    }
}
