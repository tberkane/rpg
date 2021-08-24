package ch.epfl.cs107.play.signal.logic;

import java.util.ArrayList;
import java.util.List;

public class LogicNumber extends LogicSignal {
	/** The number which the signals in e have to form in binary */
	private float nb;
	/** List of signals */
	private List<Logic> e;

	/**
	 * Default LogicNumber constructor.
	 * 
	 * @param nb (float): The number which the signals in e have to form in binary
	 * @param e  (Logic...): List of signals
	 */
	public LogicNumber(float nb, Logic... e) {
		this.nb = nb;
		this.e = new ArrayList<Logic>();
		for (Logic s : e) {
			this.e.add(s);
		}
	}

	@Override
	public boolean isOn() {
		int signalNumber = 0;

		for (int i = 0; i < e.size(); i++) {
			signalNumber += Math.pow(2, i) * e.get(e.size() - i - 1).getIntensity();
		}
		if (signalNumber != nb || e.size() > 12 || nb < 0 || nb > Math.pow(2, e.size()))
			return false;
		return true;
	}
}
