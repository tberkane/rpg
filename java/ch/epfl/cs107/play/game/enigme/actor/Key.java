package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Key extends Collectable implements Logic {

	/**
	 * Default Key constructor
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param orientation (Orientation): Initial orientation of the entity in the
	 *                    Area. Not null
	 * @param position    (DiscreteCoordinates): Initial position of the entity in
	 *                    the Area. Not null
	 */
	public Key(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, "key.1");
	}

	@Override
	public boolean isOn() {
		return isCollected();
	}
}
