package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/**
 * Actors representing a foreground, which is overlayed over the Background and
 * the other actors to create an impression of depth
 */
public class Foreground extends Entity {
	/** Sprite of the actor */
	private final ImageGraphics sprite;

	/**
	 * Default Foreground Constructor, by default the Foreground image is using the
	 * area title as file name
	 * 
	 * @param area (Area): ownerArea. Not null
	 */
	public Foreground(Area area) {
		super(DiscreteCoordinates.ORIGIN.toVector());
		sprite = new ImageGraphics(ResourcePath.getForegrounds(area.getTitle()), area.getWidth(), area.getHeight(),
				null, Vector.ZERO, 1f, 1);
		sprite.setParent(this);
	}

	/**
	 * Extended Foreground Constructor, by default the Foreground image is using the
	 * area title as file name
	 * 
	 * @param area   (Area): ownerArea. Not null
	 * @param region (RegionOfInterest): region of interest in the image for the
	 *               foreground, may be null
	 */
	public Foreground(Area area, RegionOfInterest region) {
		super(DiscreteCoordinates.ORIGIN.toVector());
		sprite = new ImageGraphics(ResourcePath.getForegrounds(area.getTitle()), area.getWidth(), area.getHeight(),
				region, Vector.ZERO, 1.0f, 1);
		sprite.setParent(this);
	}

	/**
	 * Extended Foreground Constructor
	 * 
	 * @param area   (Area): ownerArea. Not null
	 * @param region (RegionOfInterest): region of interest in the image for the
	 *               foreground, may be null
	 * @param name   (String): Foreground file name (i.e only the name, with neither
	 *               path, nor file extension). Not null
	 */
	public Foreground(Area area, RegionOfInterest region, String name) {
		super(DiscreteCoordinates.ORIGIN.toVector());
		sprite = new ImageGraphics(ResourcePath.getForegrounds(name), area.getWidth(), area.getHeight(), region,
				Vector.ZERO, 1.0f, 1);
		sprite.setParent(this);
	}

	/**
	 * Alternative Foreground Constructor
	 * 
	 * @param name   (String): Foreground file name (i.e only the name, with neither
	 *               path, nor file extension). Not null
	 * @param width  (int): of the desired foreground
	 * @param height (int): of the desired foreground
	 * @param region (RegionOfInterest): region of interest in the image for the
	 *               foreground. May be null
	 */
	public Foreground(String name, int width, int height, RegionOfInterest region) {
		super(DiscreteCoordinates.ORIGIN.toVector());
		sprite = new ImageGraphics(ResourcePath.getForegrounds(name), width, height, region, Vector.ZERO, 1.0f, 1);
		sprite.setParent(this);
	}

	/**
	 * Alternative Foreground Constructor, used when the Foreground should be
	 * anchored to a certain position
	 * 
	 * @param spriteName (String): Foreground file name (i.e only the name, with
	 *                   neither path, nor file extension). Not null
	 * @param anchor     (Vector): foreground anchor, not null
	 */
	public Foreground(String spriteName, Vector anchor) {
		super(DiscreteCoordinates.ORIGIN.toVector());
		sprite = new ImageGraphics(ResourcePath.getForegrounds(spriteName), 30, 30, null, anchor.sub(14.5f, 14.5f), 1f,
				1);
		sprite.setParent(this);
	}

	/// Foreground implements Graphics

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
	}
}
