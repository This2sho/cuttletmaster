package PorkCutlet.master.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {
    @Id
    @GeneratedValue
    @Column(name = "restaurant_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fork_cutlet_id")
    private ForkCutlet forkCutlet;


    @Embedded
    private Address address;

    private String name;


    @Builder
    public Restaurant(String name, Address address, ForkCutlet forkCutlet) {
        this.name = name;
        this.address = address;
        this.forkCutlet = forkCutlet;
    }

    public void update(String forkCutletName, ForkCutletType forkCutletType, String restaurantName, Address address) {
        this.forkCutlet.update(forkCutletName, forkCutletType);
        this.name = restaurantName;
        this.address = address;
    }
}
