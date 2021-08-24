package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

/** A decorative environment element whose sprite is defined at construction */
public class Decor extends AreaEntity {
	/** Associated sprite */
	private Sprite sprite;

	/**
	 * Default Decor constructor
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param orientation (Orientation): Initial orientation of the entity in the
	 *                    Area. Not null
	 * @param position    (DiscreteCoordinates): Initial position of the entity in
	 *                    the Area. Not null
	 * @param spriteName  (String): the name of the sprite representing the Decor
	 *                    entity.
	 */
	public Decor(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName) {
		super(area, orientation, position);
		this.sprite = new Sprite(spriteName, 1, 1, this);
	}

	@Override
	public boolean takeCellSpace() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
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
