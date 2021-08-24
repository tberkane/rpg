package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class PressurePlate extends Switchable {
	/** The time in seconds for which the pressure plate should stay activated when pressed */
	private final float activationTime;
	/**
	 * Accumulates all the deltaTimes (time since last update) into a single sum to
	 * see when the activationTime has been reached
	 */
	private float deltaTimeSum;

	/**
	 * Default PressurePlate constructor
	 * 
	 * @param area           (Area): Owner area. Not null
	 * @param orientation    (Orientation): Initial orientation of the entity in the
	 *                       Area. Not null
	 * @param position       (DiscreteCoordinates): Initial position of the entity
	 *                       in the Area. Not null
	 * @param activationTime (float): The time in seconds for which the pressure plate should
	 *                       stay activated when pressed
	 */
	public PressurePlate(Area area, Orientation orientation, DiscreteCoordinates position, float activationTime) {
		super(area, orientation, position, false, "GroundPlateOff", "GroundLightOn");
		this.activationTime = activationTime;
		this.deltaTimeSum = 0;
	}
	/**
	 * Alternative constructor where activationTime is set to 3 seconds by default
	 * 
	 * @param area           (Area): Owner area. Not null
	 * @param orientation    (Orientation): Initial orientation of the entity in the
	 *                       Area. Not null
	 * @param position       (DiscreteCoordinates): Initial position of the entity
	 *                       in the Area. Not null
	 */
	public PressurePlate(Area area, Orientation orientation, DiscreteCoordinates position) {
		this(area, orientation, position, 3.0f);
	}

	@Override
	public void toggle() {
		switchOn();
		deltaTimeSum = 0;
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
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
	}

	@Override
	public void update(float deltaTime) {
		if (isOn()) {
			deltaTimeSum += deltaTime;
			if (deltaTimeSum > activationTime) {
				switchOff();
				deltaTimeSum = 0;
			}
		}
	}
}