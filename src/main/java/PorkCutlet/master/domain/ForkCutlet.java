package PorkCutlet.master.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ForkCutlet {
    @Id
    @GeneratedValue
    @Column(name = "fork_cutlet_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ForkCutletType forkCutletType;

    private String name;

    @Builder
    public ForkCutlet(String name, ForkCutletType forkCutletType) {
        this.name = name;
        this.forkCutletType = forkCutletType;
    }

    public void update(String name, ForkCutletType forkCutletType) {
        this.forkCutletType = forkCutletType;
        this.name = name;
    }
}
