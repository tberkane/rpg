package ch.epfl.cs107.play.signal.logic;

import ch.epfl.cs107.play.signal.Signal;

/** Represents binary signals which do not depend on time. */
public interface Logic extends Signal {
	public final static Logic TRUE = new Logic() {
		@Override
		public boolean isOn() {
			return true;
		}
	};

	public final static Logic FALSE = new Logic() {
		@Override
		public boolean isOn() {
			return false;
		}
	};

	/**
	 * @return (boolean): Whether the binary signal is on
	 */
	boolean isOn();

	/**
	 * @return (float): 1 if on, 0 if off
	 */
	default float getIntensity() {
		return (isOn() ? 1.0f : 0.0f);
	}

	@Override
	default float getIntensity(float t) {
		return getIntensity();
	}
}
