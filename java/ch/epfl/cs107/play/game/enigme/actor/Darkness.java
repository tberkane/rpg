package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

/**
 * Darkness is a lightHalo overlay whose activation depends on a signal. The
 * overlay is centered on the area's viewCenter (most often the player).
 */
public class Darkness extends AreaEntity {
	/** The halo is only drawn if a corresponding signal is on */
	private Logic signal;

	/**
	 * Default Darkness constructor
	 * 
	 * @param area   (Area): Owner area. Not null
	 * @param signal (Logic): the signal on which the overlay's activation depends
	 */
	public Darkness(Area area, Logic signal) {
		super(area, Orientation.DOWN, new DiscreteCoordinates(20, 10));
		this.signal = signal;
	}

	@Override
	public void draw(Canvas canvas) {
		if (signal.getIntensity() == 1) {
			Foreground lightHalo = new Foreground("lightHalo2", getOwnerArea().getViewCenter());
			lightHalo.draw(canvas);
		}
	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

}
