package ch.epfl.cs107.play.game.enigme.area.demo2;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.enigme.Demo2;
import ch.epfl.cs107.play.game.enigme.Demo2Behavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class Demo2Area extends Area {
	/** The behavior which characterizes the area */
	private Demo2Behavior b;

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			b = new Demo2Behavior(window, getTitle());
			setBehavior(b);
			registerActor(new Background(this));
			return true;
		}
		return false;
	}

	/**
	 * @param coordinates (DiscreteCoordinates): the coordinates we are interested
	 *                    in
	 * @return (boolean): Whether the cell at coordinates corresponds to a door
	 */
	public boolean isDoor(List<DiscreteCoordinates> coordinates) {
		return b.isDoor(coordinates);
	}

	@Override
	public float getCameraScaleFactor() {
		return Demo2.cameraScale;
	}
}
