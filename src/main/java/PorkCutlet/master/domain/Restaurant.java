package PorkCutlet.master.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
