package PorkCutlet.master.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
