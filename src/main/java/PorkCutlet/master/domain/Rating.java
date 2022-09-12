package PorkCutlet.master.domain;

import lombok.Getter;

@Getter
public enum Rating {
    ONE(1), TWO(2), THREE(3), FOUR(4), Five(5);

    private int value;

    Rating(int value) {
        this.value = value;
    }

}
