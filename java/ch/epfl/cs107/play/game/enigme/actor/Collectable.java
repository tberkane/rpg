package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/**
 * AreaEntities which can be collected.
 */
public abstract class Collectable extends AreaEntity {
	/** The Sprite representing the Collectable */
	private Sprite sprite;
	/** Whether the Collectable has been collected yet */
	private boolean collected;

	/**
	 * Default Collectable constructor
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param orientation (Orientation): Initial orientation of the entity in the
	 *                    Area. Not null
	 * @param position    (DiscreteCoordinates): Initial position of the entity in
	 *                    the Area. Not null
	 * @param spriteName  (String): the collectable's associated sprite's name
	 */
	public Collectable(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName) {
		super(area, orientation, position);
		this.sprite = new Sprite(spriteName, 0.75f, 0.75f, this, null, new Vector(0.12f, 0.12f));
		this.collected = false;
	}

	/** The Collectable is picked up and disappears from its ownerArea. */
	protected void collect() {
		getOwnerArea().leaveAreaCells(this, getCurrentCells());
		getOwnerArea().unregisterActor(this);
		collected = true;
	}

	/** @return (boolean): whether the Collectable has been collected yet */
	protected boolean isCollected() {
		return collected;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
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

}
