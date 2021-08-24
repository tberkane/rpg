package ch.epfl.cs107.play.signal.logic;

import java.util.LinkedList;
import java.util.List;

public class MultipleAnd extends LogicSignal {
	/** List of signals on which MultipleAnd depends */
	private List<Logic> signals;

	/**
	 * Default MultipleAnd constructor
	 * 
	 * @param signals (Logic...): List of signals on which MultipleAnd depends
	 */
	public MultipleAnd(Logic... signals) {
		this.signals = new LinkedList<>();
		for (Logic s : signals) {
			this.signals.add(s);
		}
	}

	@Override
	public boolean isOn() {
		for (Logic s : signals) {
			if (s == null || !s.isOn())
				return false;
		}
		return true;
	}
}
