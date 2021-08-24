package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;

import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.game.enigme.actor.TalkingEntity;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class Enigme2 extends EnigmeArea {
	@Override
	public String getTitle() {
		return "Enigme2";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			registerActor(new Foreground(this));

			// Royal cup
			DiscreteCoordinates coordCup = new DiscreteCoordinates(7, 5);
			TalkingEntity cup = new TalkingEntity(this, Orientation.DOWN, coordCup, "cup.1",
					"Congratulations! You found the king's royal cup! To return to the level selector, simply walk out the door.");
			enterAreaCells(cup, Arrays.asList(coordCup));
			registerActor(cup);

			// Door to return to LevelSelector
			DiscreteCoordinates coordSd = new DiscreteCoordinates(7, 0);
			SignalDoor sd = new SignalDoor(this, "LevelSelector", new DiscreteCoordinates(5, 6), Orientation.DOWN,
					coordSd, Logic.TRUE);
			enterAreaCells(sd, Arrays.asList(coordSd));
			registerActor(sd);

			return true;
		}
		return false;
	}
}
