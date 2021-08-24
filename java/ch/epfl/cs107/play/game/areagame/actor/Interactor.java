package ch.epfl.cs107.play.game.areagame.actor;

import java.util.List;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Models objects asking for interaction (i.e. can interact with some
 * Interactable)
 * 
 * @see Interactable This interface makes sense only in the "Area Context" with
 *      Actor contained into Area Cell
 */
public interface Interactor {
	
	/** @return (DiscreteCoordinates): list of cells currently occupied */
	List<DiscreteCoordinates> getCurrentCells();

	/** @return (DiscreteCoordinates): list of cells in the field of view */
	List<DiscreteCoordinates> getFieldOfViewCells();

	/**
	 * @return (boolean): whether the Interactor is asking for contact interaction
	 */
	boolean wantsCellInteraction();

	/**
	 * @return (boolean): whether the Interactor is asking for distance interaction
	 */
	boolean wantsViewInteraction();

	/**
	 * Interact with an Interactable.
	 * 
	 * @param other (Interactable): the Interactable with which to interact
	 * 
	 */
	void interactWith(Interactable other);
}
