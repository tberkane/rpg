package ch.epfl.cs107.play.signal.logic;

public class And extends LogicSignal {
	/** First signal on which And depends */
	private Logic s1;
	/** Second signal on which And depends */
	private Logic s2;

	/**
	 * Default And constructor.
	 * 
	 * @param s1 (Logic): first signal on which And depends
	 * @param s2 (Logic): seconds signal on which And depends
	 */
	public And(Logic s1, Logic s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public boolean isOn() {
		if (s1 != null && s2 != null && s1.isOn() && s2.isOn())
			return true;
		return false;
	}
}
