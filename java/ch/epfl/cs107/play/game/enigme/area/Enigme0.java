package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;

import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class Enigme0 extends EnigmeArea {

	@Override
	public String getTitle() {
		return "Enigme0";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			registerActor(new Foreground(this));

			DiscreteCoordinates coordExit = new DiscreteCoordinates(10, 29);

			SignalDoor exitDoor = new SignalDoor(this, "LevelSelector", new DiscreteCoordinates(4, 6), Orientation.DOWN, coordExit,
					Logic.TRUE);
			enterAreaCells(exitDoor, Arrays.asList(coordExit));
			registerActor(exitDoor);
			return true;

		}
		return false;
	}

}
