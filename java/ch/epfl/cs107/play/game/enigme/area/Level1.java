package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Level1 extends EnigmeArea {

	@Override
	public String getTitle() {
		return "Level1";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			DiscreteCoordinates coord = new DiscreteCoordinates(5, 0);

			enterAreaCells(new Door(this, "LevelSelector", new DiscreteCoordinates(1, 6), Orientation.DOWN, coord),
					Arrays.asList(coord));
			return true;
		}
		return false;
	}
}
