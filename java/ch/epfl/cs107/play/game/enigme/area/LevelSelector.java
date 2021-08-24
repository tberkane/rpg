package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class LevelSelector extends EnigmeArea {

	@Override
	public String getTitle() {
		return "LevelSelector";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			DiscreteCoordinates coord1 = new DiscreteCoordinates(1, 7);
			DiscreteCoordinates coord2 = new DiscreteCoordinates(2, 7);
			DiscreteCoordinates coord3 = new DiscreteCoordinates(3, 7);
			DiscreteCoordinates coord4 = new DiscreteCoordinates(4, 7);
			DiscreteCoordinates coord5 = new DiscreteCoordinates(5, 7);

			SignalDoor sd1 = new SignalDoor(this, "Level1", new DiscreteCoordinates(5, 1), Orientation.DOWN, coord1,
					Logic.TRUE);
			enterAreaCells(sd1, Arrays.asList(coord1));
			registerActor(sd1);

			SignalDoor sd2 = new SignalDoor(this, "Level2", new DiscreteCoordinates(5, 1), Orientation.DOWN, coord2,
					Logic.TRUE);
			enterAreaCells(sd2, Arrays.asList(coord2));
			registerActor(sd2);

			SignalDoor sd3 = new SignalDoor(this, "Level3", new DiscreteCoordinates(5, 1), Orientation.DOWN, coord3,
					Logic.TRUE);
			enterAreaCells(sd3, Arrays.asList(coord3));
			registerActor(sd3);

			SignalDoor sd4 = new SignalDoor(this, "Enigme0", new DiscreteCoordinates(10, 10), Orientation.DOWN, coord4,
					Logic.TRUE);
			enterAreaCells(sd4, Arrays.asList(coord4));
			registerActor(sd4);

			SignalDoor sd5 = new SignalDoor(this, "Enigme1", new DiscreteCoordinates(16, 0), Orientation.DOWN, coord5,
					Logic.TRUE);
			enterAreaCells(sd5, Arrays.asList(coord5));
			registerActor(sd5);

			for (int i = 6; i < 9; i++) {
				DiscreteCoordinates coord = new DiscreteCoordinates(i, 7);
				SignalDoor sd = new SignalDoor(this, "", new DiscreteCoordinates(i, 6), Orientation.DOWN, coord,
						Logic.FALSE);
				enterAreaCells(sd, Arrays.asList(coord));
				registerActor(sd);
			}
			return true;
		}
		return false;
	}

}
