package ch.epfl.cs107.play.game.enigme.actor.demo2;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.enigme.area.demo2.Demo2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Demo2Player extends MovableAreaEntity {
	/** Whether the player is about to enter a door */
	private boolean isEnteringDoor;
	/** The Sprite representing the player */
	Sprite sprite;
	/** Animation duration in number of frames */
	private final static int ANIMATION_DURATION = 8;

	/**
	 * Creates a new Demo2Player.
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param coordinates (DiscreteCoordinates): Initial position of the player. Not
	 *                    null
	 */
	public Demo2Player(Area area, DiscreteCoordinates coordinates) {
		this(area, Orientation.DOWN, coordinates);
	}

	/**
	 * Creates a new Demo2Player.
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param orientation (Orientation): Initial orientation of the player. Not null
	 * @param coordinates (DiscreteCoordinates): Initial position of the player. Not
	 *                    null
	 */
	public Demo2Player(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates);
		this.sprite = new Sprite("ghost.1", 1, 1.f, this);
	}

	/**
	 * Allows the player to enter an area at a certain position.
	 * 
	 * @param area     (Area): the area to enter
	 * @param position (DiscreteCoordinates): the position the player starts at in
	 *                 the given area
	 */
	public void enterArea(Area area, DiscreteCoordinates position) {
		area.registerActor(this);
		setOwnerArea(area);
		this.setCurrentPosition(position.toVector());
		resetMotion();
	}

	/**
	 * Allows the player to leave its current area.
	 */
	public void leaveArea() {
		getOwnerArea().unregisterActor(this);
	}

	/**
	 * @param isEnteringDoor (boolean): whether the player is about to enter a door
	 */
	public void setEnteringDoor(boolean isEnteringDoor) {
		this.isEnteringDoor = isEnteringDoor;
	}

	/**
	 * @return (boolean): whether the player is about to enter a door
	 */
	public boolean isEnteringDoor() {
		return isEnteringDoor;
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
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
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);

	}

	@Override
	public void update(float deltaTime) {
		Keyboard keyboard = getOwnerArea().getKeyboard();
		Button downArrow = keyboard.get(Keyboard.DOWN);
		Button upArrow = keyboard.get(Keyboard.UP);
		Button leftArrow = keyboard.get(Keyboard.LEFT);
		Button rightArrow = keyboard.get(Keyboard.RIGHT);

		if (downArrow.isDown()) {
			if (getOrientation() == Orientation.DOWN) {
				move(ANIMATION_DURATION);
			} else {
				setOrientation(Orientation.DOWN);
			}
		}
		if (upArrow.isDown()) {
			if (getOrientation() == Orientation.UP) {
				move(ANIMATION_DURATION);
			} else {
				setOrientation(Orientation.UP);
			}
		}
		if (leftArrow.isDown()) {
			if (getOrientation() == Orientation.LEFT) {
				move(ANIMATION_DURATION);
			} else {
				setOrientation(Orientation.LEFT);
			}
		}
		if (rightArrow.isDown()) {
			if (getOrientation() == Orientation.RIGHT) {
				move(ANIMATION_DURATION);
			} else {
				setOrientation(Orientation.RIGHT);
			}
		}
		super.update(deltaTime);
	}

	@Override
	protected boolean move(int framesForMove) {
		boolean moves = super.move(framesForMove);
		if (moves) {
			Demo2Area a = (Demo2Area) getOwnerArea();
			isEnteringDoor = (a.isDoor(getEnteringCells()));
		}
		return moves;
	}

}
