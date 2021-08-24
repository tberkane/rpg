package ch.epfl.cs107.play.game.areagame.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

/**
 * Actors living in a grid
 */
public abstract class AreaEntity extends Entity implements Interactable {
	/** an AreaEntity knows its own Area */
	private Area ownerArea;
	/** Orientation of the AreaEntity in the Area */
	private Orientation orientation;
	/** Coordinate of the main Cell linked to the entity */
	private DiscreteCoordinates currentMainCellCoordinates;

	/**
	 * Default AreaEntity constructor
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param orientation (Orientation): Initial orientation of the entity in the
	 *                    Area. Not null
	 * @param position    (DiscreteCoordinates): Initial position of the entity in
	 *                    the Area. Not null
	 */
	public AreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(position.toVector());
		this.ownerArea = area;
		this.orientation = orientation;
		this.currentMainCellCoordinates = position;
	}

	/**
	 * Getter for the main cell's coordinates.
	 * 
	 * @return (DiscreteCoordinates): the coordinates of the main cell occupied by
	 *         the AreaEntity
	 */
	protected DiscreteCoordinates getCurrentMainCellCoordinates() {
		return currentMainCellCoordinates;
	}

	/**
	 * Getter for the orientation
	 * 
	 * @return (Orientation): the Orientation of the AreaEntity in the Area
	 */
	protected Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Setter for the orientation.
	 * 
	 * @param orientation (Orientation): the Orientation of the AreaEntity in the
	 *                    Area
	 */
	protected void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Getter for the area width
	 * 
	 * @return (int) : the width in number of cols
	 */
	protected final int getWidth() {
		return ownerArea.getWidth();
	}

	/**
	 * Getter for the area height
	 * 
	 * @return (int) : the height in number of rows
	 */
	protected final int getHeight() {
		return ownerArea.getHeight();
	}

	/**
	 * Setter for the owner area
	 * 
	 * @param ownerArea (Area): the new ownerArea
	 */
	protected void setOwnerArea(Area ownerArea) {
		this.ownerArea = ownerArea;
	}

	/**
	 * Getter for the owner area
	 * 
	 * @return (Area): the ownerArea
	 */
	protected Area getOwnerArea() {
		return ownerArea;
	}

	@Override
	protected void setCurrentPosition(Vector v) {
		if (DiscreteCoordinates.isCoordinates(v)) {
			super.setCurrentPosition(v.round());
			currentMainCellCoordinates = new DiscreteCoordinates((int) v.round().x, (int) v.round().y);
		} else {
			super.setCurrentPosition(v);
		}
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}
}
