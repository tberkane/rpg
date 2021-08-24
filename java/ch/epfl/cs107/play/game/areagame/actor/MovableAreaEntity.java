package ch.epfl.cs107.play.game.areagame.actor;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

/**
 * MovableAreaEntity are AreaEntity able to move on a grid
 */
public abstract class MovableAreaEntity extends AreaEntity {
	/** Indicates if the actor is currently moving */
	private boolean isMoving;
	/** Indicates how many frames the current move is supposed to take */
	private int framesForCurrentMove;
	/** The target cell (i.e. where the mainCell will be after the motion) */
	private DiscreteCoordinates targetMainCellCoordinates;

	/**
	 * Default MovableAreaEntity constructor
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param position    (Coordinate): Initial position of the entity. Not null
	 * @param orientation (Orientation): Initial orientation of the entity. Not null
	 */
	public MovableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		resetMotion();
	}

	/** @return (boolean): whether it is moving */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * Initialize or reset the current motion information
	 */
	protected void resetMotion() {
		isMoving = false;
		framesForCurrentMove = 0;
		targetMainCellCoordinates = getCurrentMainCellCoordinates();
	}

	/**
	 * If the entity is ready to move, can leave its current cells and can enter
	 * other cells, then motion is initiated
	 * 
	 * 
	 * @param framesForMove (int): number of frames used for simulating motion
	 * @return (boolean): returns true if motion can occur
	 */
	protected boolean move(int framesForMove) {
		if (!isMoving || getCurrentMainCellCoordinates().equals(targetMainCellCoordinates)) {
			if (!(getOwnerArea().leaveAreaCells(this, getLeavingCells()))
					|| !getOwnerArea().enterAreaCells(this, getEnteringCells())) {
				return false;
			} else {
				framesForCurrentMove = (framesForMove < 1 ? 1 : framesForMove);
				Vector orientation = getOrientation().toVector();
				targetMainCellCoordinates = getCurrentMainCellCoordinates().jump(orientation);
				isMoving = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * @return (List): The cells which will be left during the
	 *         movement
	 */
	protected final List<DiscreteCoordinates> getLeavingCells() {
		return getCurrentCells();
	}

	/**
	 * @return (List): The cells which will be entered during
	 *         the movement, obtained by projecting the current cells in the
	 *         direction the entity is facing
	 */
	protected final List<DiscreteCoordinates> getEnteringCells() {
		List<DiscreteCoordinates> enteringCells = new ArrayList<>();

		for (DiscreteCoordinates coord : getCurrentCells()) {
			int xJump = coord.jump(getOrientation().toVector()).x;
			int yJump = coord.jump(getOrientation().toVector()).y;
			if (xJump >= 0 && yJump >= 0 && xJump < getWidth() && yJump < getHeight()) {
				enteringCells.add(coord.jump(getOrientation().toVector()));
			}
		}
		return enteringCells;
	}

	/**
	 * @return (DiscreteCoordinates): the target cell to which the entity is moving
	 */
	protected DiscreteCoordinates getTargetMainCellCoordinates() {
		return targetMainCellCoordinates;
	}

	/// MovableAreaEntity implements Actor

	@Override
	public void update(float deltaTime) {
		if (isMoving && !getCurrentMainCellCoordinates().equals(targetMainCellCoordinates)) {
			Vector distance = getOrientation().toVector();
			distance = distance.mul(1.0f / framesForCurrentMove);
			setCurrentPosition(getPosition().add(distance));

		} else {
			resetMotion();
		}
	}

	/// Implements Positionable

	@Override
	public Vector getVelocity() {
		return getOrientation().toVector().mul(framesForCurrentMove);
	}

	@Override
	protected void setOrientation(Orientation orientation) {
		if (!isMoving)
			super.setOrientation(orientation);
	}

}
