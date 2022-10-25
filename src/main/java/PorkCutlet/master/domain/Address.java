package PorkCutlet.master.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	private String roadAddress;
	private String detailAddress;

	@Builder
	public Address(String roadAddress, String detailAddress) {
		this.roadAddress = roadAddress;
		this.detailAddress = detailAddress;
	}

	@Override
	public String toString() {
		return roadAddress + " " + detailAddress;
	}
}
