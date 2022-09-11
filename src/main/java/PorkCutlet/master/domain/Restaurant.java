package PorkCutlet.master.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {
    @Id
    @GeneratedValue
    @Column(name = "restaurant_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @Builder
    public Restaurant(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
