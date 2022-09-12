package PorkCutlet.master.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Food {
    @Id
    @GeneratedValue
    @Column(name = "food_id")
    private Long id;

    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Builder
    public Food(String name, int price, FoodType foodType, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.foodType = foodType;
        this.restaurant = restaurant;
    }
}
