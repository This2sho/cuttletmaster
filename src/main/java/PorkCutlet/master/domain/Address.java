package PorkCutlet.master.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String roadAddress;
    private String detailAddress;
    private String postcode;

    @Builder
    public Address(String roadAddress, String detailAddress, String postcode) {
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return roadAddress + " " + detailAddress + " " + postcode;
    }
}
