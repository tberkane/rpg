package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;

import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.enigme.actor.Darkness;
import ch.epfl.cs107.play.game.enigme.actor.Decor;
import ch.epfl.cs107.play.game.enigme.actor.Fire;
import ch.epfl.cs107.play.game.enigme.actor.Ice;
import ch.epfl.cs107.play.game.enigme.actor.Lever;
import ch.epfl.cs107.play.game.enigme.actor.PressurePlate;
import ch.epfl.cs107.play.game.enigme.actor.PressureSwitch;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.game.enigme.actor.SignalRock;
import ch.epfl.cs107.play.game.enigme.actor.TalkingEntity;
import ch.epfl.cs107.play.game.enigme.actor.TeleportStone;
import ch.epfl.cs107.play.game.enigme.actor.Torch;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.LogicNumber;
import ch.epfl.cs107.play.signal.logic.MultipleAnd;
import ch.epfl.cs107.play.signal.logic.Not;
import ch.epfl.cs107.play.signal.logic.Or;
import ch.epfl.cs107.play.window.Window;

public class Enigme1 extends EnigmeArea {

	@Override
	public String getTitle() {
		return "Enigme1";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			registerActor(new Foreground(this));

			// King
			DiscreteCoordinates coordTalkingEntity1 = new DiscreteCoordinates(16, 3);
			TalkingEntity talkingEntity1 = new TalkingEntity(this, Orientation.DOWN, coordTalkingEntity1, "mob.1",
					"(Press SPACE to scroll) HELP!! I'm the king of this castle and have been transformed into this horrible beast! Would you kindly help me retrieve my original form by getting to the castle dungeon and finding my royal cup? If you succeed, you shall be covered in gold! To help you out, here are a few useful tips: the arrow keys let you move around in the world, L lets you interact with it, P pauses the game and finally press R to wear your running shoes. Good luck, have fun and bring me my cup back!!");
			enterAreaCells(talkingEntity1, Arrays.asList(coordTalkingEntity1));
			registerActor(talkingEntity1);

			// Array of pressure Switches in first zone
			Logic[] pressureSwitches = new Logic[60];

			// Adds all the pressure switches to the first zone
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 6; j++) {
					DiscreteCoordinates coordPressureSwitch = new DiscreteCoordinates(14 + i, 12 - j);
					PressureSwitch pressureSwitch = new PressureSwitch(this, Orientation.DOWN, coordPressureSwitch);
					// if a pressureSwitch can't enter its cell then that means it's a wall. So just
					// replace that pressureSwitch by a LogicTRUE in the array
					if (!enterAreaCells(pressureSwitch, Arrays.asList(coordPressureSwitch))) {
						pressureSwitches[6 * i + j] = Logic.TRUE;
					} else {
						registerActor(pressureSwitch);
						pressureSwitches[6 * i + j] = pressureSwitch;
					}
				}
			}

			// All pressure switches as a big And
			MultipleAnd pressureSwitchesAnd = new MultipleAnd(pressureSwitches);

			// SignalRock blocking initial access to maze
			DiscreteCoordinates coordMazeAccessSignalRock = new DiscreteCoordinates(23, 13);
			SignalRock mazeAccessSignalRock = new SignalRock(this, Orientation.DOWN, coordMazeAccessSignalRock,
					pressureSwitchesAnd);
			enterAreaCells(mazeAccessSignalRock, Arrays.asList(coordMazeAccessSignalRock));
			registerActor(mazeAccessSignalRock);

			// Pressure Switch which triggers Darkness, not registered as actor so that it
			// is not drawn
			DiscreteCoordinates coordDarknessTrigger = new DiscreteCoordinates(22, 14);
			PressureSwitch darknessTrigger = new PressureSwitch(this, Orientation.DOWN, coordDarknessTrigger);
			enterAreaCells(darknessTrigger, Arrays.asList(coordDarknessTrigger));

			// Campfire
			DiscreteCoordinates coordFire = new DiscreteCoordinates(22, 36);
			Fire fire = new Fire(this, Orientation.DOWN, coordFire, false);
			enterAreaCells(fire, Arrays.asList(coordFire));
			registerActor(fire);

			// TeleportStone
			DiscreteCoordinates coordTeleportStone = new DiscreteCoordinates(23, 36);
			TeleportStone teleportStone = new TeleportStone(this, Orientation.DOWN, coordTeleportStone,
					new DiscreteCoordinates(22, 2));
			enterAreaCells(teleportStone, Arrays.asList(coordTeleportStone));
			registerActor(teleportStone);

			Not notFire = new Not(fire);
			Not notTeleportStone = new Not(teleportStone);
			// Darkness overlay will be turned on if it has been triggered, the fire has not
			// been lit, and the teleport stone has not been collected
			MultipleAnd darknessCondition = new MultipleAnd(darknessTrigger, notFire, notTeleportStone);

			// Darkness overlay
			Darkness darkness = new Darkness(this, darknessCondition);
			registerActor(darkness);

			// SignalRock preventing from leaving maze will disappear once the darkness is
			// no more
			Not notDarkness = new Not(darknessCondition);

			// SignalRock preventing from leaving maze
			DiscreteCoordinates coordMazeSignalRock = new DiscreteCoordinates(22, 14);
			SignalRock mazeSignalRock = new SignalRock(this, Orientation.DOWN, coordMazeSignalRock, notDarkness);
			enterAreaCells(mazeSignalRock, Arrays.asList(coordMazeSignalRock));
			registerActor(mazeSignalRock);

			// SignalRock blocking access to ice zone will disappear once fire has been
			// activated or teleportStone has been collected
			Or or5 = new Or(fire, teleportStone);

			// SignalRock blocking access to ice zone
			DiscreteCoordinates coordIceSignalRock = new DiscreteCoordinates(14, 5);
			SignalRock iceSignalRock = new SignalRock(this, Orientation.DOWN, coordIceSignalRock, or5);
			enterAreaCells(iceSignalRock, Arrays.asList(coordIceSignalRock));
			registerActor(iceSignalRock);

			// Adds all the ice tiles to the ice level
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 8; j++) {
					DiscreteCoordinates coordIce = new DiscreteCoordinates(1 + i, 8 - j);
					Ice ice = new Ice(this, Orientation.DOWN, coordIce);
					enterAreaCells(ice, Arrays.asList(coordIce));
					registerActor(ice);
				}
			}

			// Adding rocks which are used as obstacles in the ice level
			for (int i = 0; i < 3; i++) {
				DiscreteCoordinates coordRock1 = new DiscreteCoordinates(12, 8 - i);
				Decor rock1 = new Decor(this, Orientation.DOWN, coordRock1, "rock.3");
				enterAreaCells(rock1, Arrays.asList(coordRock1));
				registerActor(rock1);

				DiscreteCoordinates coordRock2 = new DiscreteCoordinates(12, 1 + i);
				Decor rock2 = new Decor(this, Orientation.DOWN, coordRock2, "rock.3");
				enterAreaCells(rock2, Arrays.asList(coordRock2));
				registerActor(rock2);

			}

			DiscreteCoordinates coordRock3 = new DiscreteCoordinates(12, 4);
			Decor rock3 = new Decor(this, Orientation.DOWN, coordRock3, "rock.3");
			enterAreaCells(rock3, Arrays.asList(coordRock3));
			registerActor(rock3);

			// Array with positions of all the obstacle rocks
			int[][] positions = new int[9][2];

			positions[0] = new int[] { 1, 1 };
			positions[1] = new int[] { 1, 7 };
			positions[2] = new int[] { 4, 1 };
			positions[3] = new int[] { 5, 6 };
			positions[4] = new int[] { 5, 8 };
			positions[5] = new int[] { 9, 5 };
			positions[6] = new int[] { 9, 8 };
			positions[7] = new int[] { 10, 2 };
			positions[8] = new int[] { 11, 2 };

			for (int i = 0; i < positions.length; i++) {
				DiscreteCoordinates coordRock = new DiscreteCoordinates(positions[i][0], positions[i][1]);
				Decor rock = new Decor(this, Orientation.DOWN, coordRock, "rock.3");
				enterAreaCells(rock, Arrays.asList(coordRock));
				registerActor(rock);
			}

			// Old man
			DiscreteCoordinates coordTalkingEntity2 = new DiscreteCoordinates(6, 15);
			TalkingEntity talkingEntity2 = new TalkingEntity(this, Orientation.DOWN, coordTalkingEntity2, "old.man.1",
					"Greetings traveler, may I ask, what is the answer to the ultimate question of life, the universe, and everything?");
			enterAreaCells(talkingEntity2, Arrays.asList(coordTalkingEntity2));
			registerActor(talkingEntity2);

			// Array of torches used for answering the riddle in binary
			Logic[] torches = new Logic[8];

			// Adds all the torches to the riddle zone
			for (int i = 0; i < 8; i++) {
				DiscreteCoordinates coordTorch = new DiscreteCoordinates(3 + i, 13);
				Torch torch = new Torch(this, Orientation.DOWN, coordTorch, false);
				enterAreaCells(torch, Arrays.asList(coordTorch));
				registerActor(torch);
				torches[i] = torch;
			}

			// The torches must form the number 42
			LogicNumber torchNumber = new LogicNumber(42, torches);

			// The 2 SignalRocks which disappear after the riddle is solved
			for (int j = 0; j < 2; j++) {
				DiscreteCoordinates coordRiddleSignalRock = new DiscreteCoordinates(6 + j, 17);
				SignalRock riddleSignalRock = new SignalRock(this, Orientation.DOWN, coordRiddleSignalRock,
						torchNumber);
				enterAreaCells(riddleSignalRock, Arrays.asList(coordRiddleSignalRock));
				registerActor(riddleSignalRock);
			}

			// The 3 pressure plates for the speed zone
			// First pressure plate
			DiscreteCoordinates coordPp1 = new DiscreteCoordinates(7, 20);
			PressurePlate pp1 = new PressurePlate(this, Orientation.DOWN, coordPp1, 3);
			enterAreaCells(pp1, Arrays.asList(coordPp1));
			registerActor(pp1);
			// Second pressure plate
			DiscreteCoordinates coordPp2 = new DiscreteCoordinates(5, 22);
			PressurePlate pp2 = new PressurePlate(this, Orientation.DOWN, coordPp2, 3);
			enterAreaCells(pp2, Arrays.asList(coordPp2));
			registerActor(pp2);
			// Third pressure plate
			DiscreteCoordinates coordPp3 = new DiscreteCoordinates(7, 24);
			PressurePlate pp3 = new PressurePlate(this, Orientation.DOWN, coordPp3, 3);
			enterAreaCells(pp3, Arrays.asList(coordPp3));
			registerActor(pp3);

			// All the pressure plates have to be activated for the final SignalRock to
			// disappear
			MultipleAnd ppAnd = new MultipleAnd(pp1, pp2, pp3);

			// A lever to deactivate the final SignalRock once the speed zone has been
			// passed
			DiscreteCoordinates coordLever = new DiscreteCoordinates(8, 29);
			Lever lever = new Lever(this, Orientation.DOWN, coordLever);
			enterAreaCells(lever, Arrays.asList(coordLever));
			registerActor(lever);

			// Two ways to deactivate the final SignalRock: have all the pressure plates
			// activated or activate the lever
			Or ppLeverOr = new Or(ppAnd, lever);

			// Final SignalRock
			DiscreteCoordinates coordFinalSignalRock = new DiscreteCoordinates(6, 27);
			SignalRock finalSignalRock = new SignalRock(this, Orientation.DOWN, coordFinalSignalRock, ppLeverOr);
			enterAreaCells(finalSignalRock, Arrays.asList(coordFinalSignalRock));
			registerActor(finalSignalRock);

			// Door to Enigme2
			DiscreteCoordinates coordDoor = new DiscreteCoordinates(6, 32);
			SignalDoor door = new SignalDoor(this, "Enigme2", new DiscreteCoordinates(7, 1), Orientation.DOWN,
					coordDoor, Logic.TRUE);
			enterAreaCells(door, Arrays.asList(coordDoor));
			registerActor(door);

			return true;
		}
		return false;
	}
}
