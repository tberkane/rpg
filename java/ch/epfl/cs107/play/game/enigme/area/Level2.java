package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Apple;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Level2 extends EnigmeArea {

	@Override
	public String getTitle() {
		return "Level2";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			DiscreteCoordinates coord1 = new DiscreteCoordinates(5, 0);
			DiscreteCoordinates coord2 = new DiscreteCoordinates(5, 6);

			enterAreaCells(new Door(this, "LevelSelector", new DiscreteCoordinates(2, 6), Orientation.DOWN, coord1),
					Arrays.asList(coord1));

			Apple apple = new Apple(this, Orientation.DOWN, coord2);
			enterAreaCells(apple, Arrays.asList(coord2));
			registerActor(apple);
		}
		return false;
	}

}
