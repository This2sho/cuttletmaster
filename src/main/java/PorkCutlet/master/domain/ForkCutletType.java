package PorkCutlet.master.domain;

public enum ForkCutletType {
	KOREAN_STYLE(0), JAPANESE_STYLE(1);

	private int value;

	ForkCutletType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		if (this.value == 0)
			return "경양식";
		return "일식";
	}
}
