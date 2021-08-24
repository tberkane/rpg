package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.enigme.Enigme;
import ch.epfl.cs107.play.game.enigme.EnigmeBehavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public abstract class EnigmeArea extends Area {

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			EnigmeBehavior b = new EnigmeBehavior(window, getTitle());
			setBehavior(b);
			registerActor(new Background(this));
			return true;
		}
		return false;
	}

	@Override
	public float getCameraScaleFactor() {
		return Enigme.cameraScale;
	}
}
