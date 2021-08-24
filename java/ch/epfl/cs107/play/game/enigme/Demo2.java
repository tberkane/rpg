package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.enigme.actor.demo2.Demo2Player;
import ch.epfl.cs107.play.game.enigme.area.demo2.Room0;
import ch.epfl.cs107.play.game.enigme.area.demo2.Room1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Demo2 extends AreaGame {
	/** The scale for the camera */
	public static final int cameraScale = 22;
	/** The player */
	private Demo2Player player;

	@Override
	public int getFrameRate() {
		return 20;
	}

	@Override
	public String getTitle() {
		return "Demo2";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		Room0 room0 = new Room0();
		Room1 room1 = new Room1();
		
		addArea(room0);
		addArea(room1);
		
		setCurrentArea(room0.getTitle(), true);
		this.player = new Demo2Player(room0, new DiscreteCoordinates(5, 5));
		getCurrentArea().registerActor(player);
		getCurrentArea().setViewCandidate(player);
		return true;
	}

	@Override
	public void update(float deltaTime) {
		if (player.isEnteringDoor() && !player.isMoving()) {
			player.setEnteringDoor(false);
			player.leaveArea();
			String currentAreaTitle = getCurrentArea().getTitle();
			if (currentAreaTitle.equals("LevelSelector"))
				player.enterArea(setCurrentArea("Level1", false), new DiscreteCoordinates(5, 2));
			else
				player.enterArea(setCurrentArea("LevelSelector", false), new DiscreteCoordinates(5, 5));
			getCurrentArea().setViewCandidate(player);
		}
		super.update(deltaTime);
	}

}
