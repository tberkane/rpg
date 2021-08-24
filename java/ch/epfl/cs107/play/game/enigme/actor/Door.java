package ch.epfl.cs107.play.game.enigme.actor;

import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Door extends AreaEntity {
	/** The name of the area to which the door brings */
	private String arrivalArea;
	/** The coordinates in the area to which the door brings */
	private DiscreteCoordinates arrivalCoordinates;
	/** The coordinates of the cells occupied by the door */
	private List<DiscreteCoordinates> positions;

	/**
	 * Default Door constructor
	 * 
	 * @param ownerArea           (Area): Owner area. Not null
	 * @param arrivalArea         (String): The name of the area to which the door
	 *                            brings
	 * @param arrivalCoordinates  (DiscreteCoordinates): The coordinates in the area
	 *                            to which the door brings
	 * @param orientation         (Orientation): Initial orientation of the entity
	 *                            in the Area. Not null
	 * @param mainCellCoordinates (DiscreteCoordinates): Coordinates of the main
	 *                            Cell linked to the entity
	 * @param positions           (DiscreteCoordinates...): Coordinates of the cells
	 *                            occupied by the door
	 */
	public Door(Area ownerArea, String arrivalArea, DiscreteCoordinates arrivalCoordinates, Orientation orientation,
			DiscreteCoordinates mainCellCoordinates, DiscreteCoordinates... positions) {
		super(ownerArea, orientation, mainCellCoordinates);
		this.arrivalArea = arrivalArea;
		this.arrivalCoordinates = arrivalCoordinates;
		this.positions = new LinkedList<>();
		this.positions.add(mainCellCoordinates);
		for (DiscreteCoordinates p : positions) {
			this.positions.add(p);
		}
	}

	/**
	 * Getter for the arrival area
	 * 
	 * @return (String) : The name of the area to which the door brings
	 */
	public String getArrivalArea() {
		return arrivalArea;
	}

	/**
	 * Getter for the arrival coordinates
	 * 
	 * @return (String) : The coordinates in the area to which the door brings
	 */
	public DiscreteCoordinates getArrivalCoordinates() {
		return arrivalCoordinates;
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return positions;
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
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
	}

}
