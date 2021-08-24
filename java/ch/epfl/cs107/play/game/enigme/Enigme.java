package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.game.enigme.area.Enigme0;
import ch.epfl.cs107.play.game.enigme.area.Enigme1;
import ch.epfl.cs107.play.game.enigme.area.Enigme2;
import ch.epfl.cs107.play.game.enigme.area.Level1;
import ch.epfl.cs107.play.game.enigme.area.Level2;
import ch.epfl.cs107.play.game.enigme.area.Level3;
import ch.epfl.cs107.play.game.enigme.area.LevelSelector;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

/**
 * Enigme Game is a concept of Game derived for AreaGame. It introduces the
 * notion of Player When initializing the player is added to the current area
 */
public class Enigme extends AreaGame {
	/** The scale for the camera */
	public static final int cameraScale = 15;
	/** The player */
	private EnigmePlayer player;

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		LevelSelector levelSelector = new LevelSelector();
		Level1 level1 = new Level1();
		Level2 level2 = new Level2();
		Level3 level3 = new Level3();
		Enigme0 enigme0 = new Enigme0();
		Enigme1 enigme1 = new Enigme1();
		Enigme2 enigme2 = new Enigme2();

		addArea(levelSelector);
		addArea(level1);
		addArea(level2);
		addArea(level3);
		addArea(enigme0);
		addArea(enigme1);
		addArea(enigme2);

		setCurrentArea(levelSelector.getTitle(), true);
		this.player = new EnigmePlayer(levelSelector, new DiscreteCoordinates(5, 5));
		getCurrentArea().registerActor(player);
		getCurrentArea().setViewCandidate(player);
		return true;
	}

	@Override
	public int getFrameRate() {
		return 20;
	}

	@Override
	public String getTitle() {
		return "Enigme";
	}

	@Override
	public void update(float deltaTime) {
		if (player.isEnteringDoor()) {
			player.setEnteringDoor(false);
			player.leaveArea();
			player.enterArea(setCurrentArea(player.passedDoor().getArrivalArea(), false),
					player.passedDoor().getArrivalCoordinates());
			getCurrentArea().setViewCandidate(player);
		}
		super.update(deltaTime);
	}

}