package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.window.Canvas;

/**
 * An Animation is made up of a staticSprite for when the animated
 * actor is immobile and of a collection animationSprites through which we cycle
 * when the actor is in motion.
 */
public class Animation {
	/**
	 * The Sprite which is displayed when the actor who owns this animation is
	 * immobile
	 */
	private final Sprite staticSprite;
	/**
	 * The collection of sprites through which we cycle when the actor who owns this
	 * animation is moving
	 */
	private final Sprite[] animationSprites;
	/** A counter for cycling through the indexes of animationSprites */
	private int counter;
	/** A delay to slow down the animation */
	private int delay;

	/**
	 * Creates a new Animation.
	 * 
	 * @param immobileSprite (Sprite): The Sprite which is displayed when the actor
	 *                       who owns this animation is immobile
	 * @param movingSprites  (Sprite[]): The collection of sprites through which we
	 *                       cycle when the actor who owns this animation is moving
	 */
	public Animation(Sprite immobileSprite, Sprite[] movingSprites) {
		this.staticSprite = immobileSprite;
		this.animationSprites = movingSprites;
		counter = 0;
		delay = 0;
	}

	/**
	 * Draws either the static sprite or cycles through the animationSprites,
	 * drawing them one after the other.
	 * 
	 * @param canvas      (Canvas): canvas target, not null
	 * @param animated    (boolean): whether the animation should be drawn as static
	 *                    or as animated
	 * @param accelerated (boolean): whether the sprites should be cycled through
	 *                    twice as fast, creating a faster animation
	 */
	public void draw(Canvas canvas, boolean animated, boolean accelerated) {
		if (animated) {
			animationSprites[counter].draw(canvas);
			delay = (delay + 1) % (accelerated ? 2 : 4);
			if (delay == 0)
				counter = (counter + 1) % animationSprites.length;
		} else {
			staticSprite.draw(canvas);
		}
	}

}
