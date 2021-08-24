package ch.epfl.cs107.play.game.areagame.actor;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Models objects receptive to interaction (i.e. Interactor can interact with
 * them)
 * 
 * @see Interactor This interface makes sense only in the "AreaGame" context
 *      with Actor contained into Area Cell
 */
public interface Interactable {
	
	/** @return (DiscreteCoordinates): list of cells currently occupied */
	List<DiscreteCoordinates> getCurrentCells();

	/**
	 * @return (boolean): whether the Interactable makes the cells it occupies
	 *         untraversable
	 */
	boolean takeCellSpace();

	/** @return (boolean): whether it accepts distant interactions */
	boolean isViewInteractable();

	/** @return (boolean): whether it accepts contact interactions */
	boolean isCellInteractable();

	/**
	 * The Interactable indicates that it accepts to take part in an interaction
	 * managed by a certain interaction handler.
	 * 
	 * @param v (AreaInteractionVisitor): a generic interaction handler
	 * 
	 */
	default public void acceptInteraction(AreaInteractionVisitor v) {
		v.interactWith(this);
	}
}
