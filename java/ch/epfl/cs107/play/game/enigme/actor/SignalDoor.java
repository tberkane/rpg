package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class SignalDoor extends Door {
	/** The signal associated with the door */
	private Logic signal;
	/** The door's sprite when it is open */
	private Sprite spriteOpen;
	/** The door's sprite when it is closed */
	private Sprite spriteClosed;

	/**
	 * Default SignalDoor constructor
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
	 * @param signal              (Signal): The signal associated with the door
	 * @param positions           (DiscreteCoordinates...): Coordinates of the cells
	 *                            occupied by the door
	 */
	public SignalDoor(Area ownerArea, String arrivalArea, DiscreteCoordinates arrivalCoordinates,
			Orientation orientation, DiscreteCoordinates mainCellCoordinates, Logic signal,
			DiscreteCoordinates... positions) {
		super(ownerArea, arrivalArea, arrivalCoordinates, orientation, mainCellCoordinates, positions);
		this.signal = signal;
		this.spriteOpen = new Sprite("door.open.1", 1, 1.f, this);
		this.spriteClosed = new Sprite("door.close.1", 1, 1.f, this);
	}

	@Override
	public boolean takeCellSpace() {
		return !signal.isOn();
	}

	@Override
	public boolean isCellInteractable() {
		return signal.isOn();
	}

	@Override
	public void draw(Canvas canvas) {
		if (signal.isOn())
			spriteOpen.draw(canvas);
		else
			spriteClosed.draw(canvas);
	}

}
