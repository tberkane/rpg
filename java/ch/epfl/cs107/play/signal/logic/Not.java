package ch.epfl.cs107.play.signal.logic;

public class Not extends LogicSignal {
	/** Signal which Not will give the opposite of */
	private Logic s;

	/**
	 * Default constructor of Not
	 * 
	 * @param s (Logic): Signal which Not will give the opposite of
	 */
	public Not(Logic s) {
		this.s = s;
	}

	@Override
	public boolean isOn() {
		if (s != null && !s.isOn())
			return true;
		return false;
	}

}
