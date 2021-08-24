package ch.epfl.cs107.play.signal.logic;

public abstract class LogicSignal implements Logic {

	public final float getIntensity() {
		return (isOn() ? 1.0f : 0.0f);
	}

	@Override
	public final float getIntensity(float t) {
		return getIntensity();
	}
}
