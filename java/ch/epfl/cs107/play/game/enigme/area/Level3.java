package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Key;
import ch.epfl.cs107.play.game.enigme.actor.Lever;
import ch.epfl.cs107.play.game.enigme.actor.PressurePlate;
import ch.epfl.cs107.play.game.enigme.actor.PressureSwitch;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.game.enigme.actor.SignalRock;
import ch.epfl.cs107.play.game.enigme.actor.Torch;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.LogicNumber;
import ch.epfl.cs107.play.signal.logic.MultipleAnd;
import ch.epfl.cs107.play.signal.logic.Or;
import ch.epfl.cs107.play.window.Window;

public class Level3 extends EnigmeArea {

	@Override
	public String getTitle() {
		return "Level3";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {

			// Key
			DiscreteCoordinates coordKey = new DiscreteCoordinates(1, 3);
			Key key = new Key(this, Orientation.DOWN, coordKey);
			enterAreaCells(key, Arrays.asList(coordKey));
			registerActor(key);

			// Torch
			DiscreteCoordinates coordTorch = new DiscreteCoordinates(7, 5);
			Torch torch = new Torch(this, Orientation.DOWN, coordTorch, false);
			enterAreaCells(torch, Arrays.asList(coordTorch));
			registerActor(torch);

			// Pressure plate
			DiscreteCoordinates coordPp = new DiscreteCoordinates(9, 8);
			PressurePlate pp = new PressurePlate(this, Orientation.DOWN, coordPp);
			enterAreaCells(pp, Arrays.asList(coordPp));
			registerActor(pp);

			// Array of pressure switches
			Logic[] psArray = new Logic[7];

			// Adding pressure switches, two at a time
			for (int i = 4; i <= 6; i++) {
				DiscreteCoordinates coordPs = new DiscreteCoordinates(i, 4);
				PressureSwitch ps = new PressureSwitch(this, Orientation.DOWN, coordPs);
				enterAreaCells(ps, Arrays.asList(coordPs));
				registerActor(ps);
				psArray[i - 4] = ps;

				DiscreteCoordinates coordPs2 = new DiscreteCoordinates(i, 6);
				PressureSwitch ps2 = new PressureSwitch(this, Orientation.DOWN, coordPs2);
				enterAreaCells(ps2, Arrays.asList(coordPs2));
				registerActor(ps2);
				psArray[i - 1] = ps2;
			}
			
			// One more pressure switch
			DiscreteCoordinates coordPs = new DiscreteCoordinates(5, 5);
			PressureSwitch ps = new PressureSwitch(this, Orientation.DOWN, coordPs);
			enterAreaCells(ps, Arrays.asList(coordPs));
			registerActor(ps);
			psArray[6] = ps;

			// Array of levers
			Logic[] leverArray = new Logic[3];

			// Adding all levers
			for (int i = 8; i <= 10; i++) {
				DiscreteCoordinates coordLever = new DiscreteCoordinates(i, 5);
				Lever lever = new Lever(this, Orientation.DOWN, coordLever);
				enterAreaCells(lever, Arrays.asList(coordLever));
				registerActor(lever);
				leverArray[i - 8] = lever;
			}

			// SignalDoor
			DiscreteCoordinates coordSd = new DiscreteCoordinates(5, 9);
			SignalDoor sd = new SignalDoor(this, "LevelSelector", new DiscreteCoordinates(3, 6), Orientation.DOWN,
					coordSd, key);
			enterAreaCells(sd, Arrays.asList(coordSd));
			registerActor(sd);

			// 1st SignalRock
			DiscreteCoordinates coordSr1 = new DiscreteCoordinates(6, 8);
			SignalRock sr1 = new SignalRock(this, Orientation.DOWN, coordSr1, pp);
			enterAreaCells(sr1, Arrays.asList(coordSr1));
			registerActor(sr1);

			// 2nd SignalRock
			DiscreteCoordinates coordSr2 = new DiscreteCoordinates(5, 8);
			MultipleAnd ma = new MultipleAnd(psArray);
			SignalRock sr2 = new SignalRock(this, Orientation.DOWN, coordSr2, ma);
			enterAreaCells(sr2, Arrays.asList(coordSr2));
			registerActor(sr2);

			// 3rd SignalRock
			DiscreteCoordinates coordSr3 = new DiscreteCoordinates(4, 8);
			LogicNumber ln = new LogicNumber(5, leverArray);
			Or or = new Or(torch, ln);
			SignalRock sr3 = new SignalRock(this, Orientation.DOWN, coordSr3, or);
			enterAreaCells(sr3, Arrays.asList(coordSr3));
			registerActor(sr3);

			return true;
		}
		return false;
	}

}
