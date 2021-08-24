package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

/**
 * AreaEntities which can be switched on and off.
 */
public abstract class Switchable extends AreaEntity implements Logic {
	/** Whether the Switchable is switched on */
	private boolean on;
	/** The animation representing the Switchable */
	private Animation animation;

	/**
	 * Default Switchable constructor
	 * 
	 * @param area          (Area): Owner area. Not null
	 * @param orientation   (Orientation): Initial orientation of the entity in the
	 *                      Area. Not null
	 * @param position      (DiscreteCoordinates): Initial position of the entity in
	 *                      the Area. Not null
	 * @param on            (boolean): Whether the Switchable is switched on
	 *                      initially.
	 * @param spriteOffName  (String): the name of the sprite associated with the
	 *                      switched off Switchable
	 * @param spritesOnNames (String...): the names of the sprites associated with the
	 *                      switched on Switchable
	 */
	public Switchable(Area area, Orientation orientation, DiscreteCoordinates position, boolean on,
			String spriteOffName, String... spritesOnNames) {
		super(area, orientation, position);
		this.on = on;
		
		Sprite spriteOFF = new Sprite(spriteOffName, 1, 1, this);
		
		Sprite[] spritesON = new Sprite[spritesOnNames.length];
		
		for (int i = 0; i < spritesOnNames.length; i++) {
			spritesON[i] = new Sprite(spritesOnNames[i], 1, 1, this);
		}
		
		this.animation = new Animation(spriteOFF, spritesON);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
	}

	/** Toggles the Switchable on and off. */
	protected void toggle() {
		on = !on;
	}

	/** Switches the Switchable on. */
	protected void switchOn() {
		this.on = true;
	}
	
	/** Switches the Switchable off. */
	protected void switchOff() {
		this.on = false;
	}

	@Override
	public boolean isOn() {
		return on;
	}

	@Override
	public void draw(Canvas canvas) {
		animation.draw(canvas, on, false);
	}
}
