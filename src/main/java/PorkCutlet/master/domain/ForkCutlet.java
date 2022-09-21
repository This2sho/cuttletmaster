package PorkCutlet.master.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ForkCutlet {
    @Id
    @GeneratedValue
    @Column(name = "food_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Enumerated(EnumType.STRING)
    private ForkCutletType forkCutletType;

    private String name;

    @Builder
    public ForkCutlet(String name, ForkCutletType forkCutletType, Restaurant restaurant) {
        this.name = name;
        this.forkCutletType = forkCutletType;
        this.restaurant = restaurant;
    }
}
