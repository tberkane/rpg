package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class SignalRock extends AreaEntity {
	/** The signal associated with the rock */
	private Logic signal;
	/** The sprite representing the rock */
	private Sprite sprite;

	/**
	 * Default SignalRock constructor
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param orientation (Orientation): Initial orientation of the entity in the
	 *                    Area. Not null
	 * @param position    (DiscreteCoordinates): Coordinates of the rock
	 * @param signal      (Logic): The signal associated with the rock
	 */
	public SignalRock(Area area, Orientation orientation, DiscreteCoordinates position, Logic signal) {
		super(area, orientation, position);
		this.signal = signal;
		this.sprite = new Sprite("rock.3", 1, 1.f, this);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		return !signal.isOn();
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return !signal.isOn();
	}

	@Override
	public void draw(Canvas canvas) {
		if (!signal.isOn())
			sprite.draw(canvas);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
	}

}
