package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/**
 * An entity which is characterized by a message which is displayed as a
 * dialogue when it is interacted with
 */
public class TalkingEntity extends AreaEntity {
	/** The sprite which represents the TalkingEntity */
	private Sprite sprite;
	/** The associated message to display in case of interaction */
	private String message;

	/**
	 * Default TalkingEntity constructor.
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param orientation (Orientation): Initial orientation of the entity in the
	 *                    Area. Not null
	 * @param position    (DiscreteCoordinates): Initial position of the entity in
	 *                    the Area. Not null
	 * @param spriteName  (String): the name of the sprite to associate
	 * @param message     (String): The associated message to display in case of
	 *                    interaction
	 */
	public TalkingEntity(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName,
			String message) {
		super(area, orientation, position);
		this.sprite = new Sprite(spriteName, 0.75f, 0.65625f * 1.5f, this, new RegionOfInterest(0, 0, 16, 21),
				new Vector(0.25f / 2, 0.32f / 2));
		this.message = message;

	}
	/** @return (String): The associated message to display in case of interaction */
	protected String getMessage() {
		return message;
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
		return false;
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
	}

}
