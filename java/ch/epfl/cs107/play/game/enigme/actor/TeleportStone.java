package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

/**
 * A TeleportStone is a Collectable which, when interacted with, teleports the
 * player to certain coordinates.
 */
public class TeleportStone extends Collectable implements Logic {
	/** The coordinates to which the TeleportStone brings */
	private DiscreteCoordinates arrivalCoordinates;

	/**
	 * Default TeleportStone constructor
	 * 
	 * @param area               (Area): Owner area. Not null
	 * @param orientation        (Orientation): Initial orientation of the entity in
	 *                           the Area. Not null
	 * @param position           (DiscreteCoordinates): Initial position of the
	 *                           entity in the Area. Not null
	 * @param arrivalCoordinates (DiscreteCoordinates): The coordinates to which the
	 *                           TeleportStone brings
	 */
	public TeleportStone(Area area, Orientation orientation, DiscreteCoordinates position,
			DiscreteCoordinates arrivalCoordinates) {
		super(area, orientation, position, "jewel.2");
		this.arrivalCoordinates = arrivalCoordinates;
	}

	/**
	 * Getter for the arrival coordinates
	 * 
	 * @return (String): The coordinates to which the TeleportStone brings
	 */
	protected DiscreteCoordinates getArrivalCoordinates() {
		return arrivalCoordinates;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
	}

	@Override
	public boolean isOn() {
		return isCollected();
	}

}
