package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/** A campfire which can be switched on and off */
public class Fire extends Switchable {

	/**
	 * Default Fire constructor
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param orientation (Orientation): Initial orientation of the entity in the
	 *                    Area. Not null
	 * @param position    (DiscreteCoordinates): Initial position of the entity in
	 *                    the Area. Not null
	 * @param on          (boolean): Whether the Fire is switched on initially.
	 */
	public Fire(Area area, Orientation orientation, DiscreteCoordinates position, boolean on) {
		super(area, orientation, position, on, "fire.off", "fire.on.1", "fire.on.2");
	}

	@Override
	public boolean takeCellSpace() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}
}
