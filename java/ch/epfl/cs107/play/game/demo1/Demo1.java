package ch.epfl.cs107.play.game.demo1;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.GraphicsEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.demo1.actor.MovingRock;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class Demo1 implements Game {
	private Window window;
	private FileSystem fileSystem;
	private Actor actor1;
	private Actor actor2;
	private float radius = 0.2f;

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		this.fileSystem = fileSystem;
		this.actor1 = new GraphicsEntity(Vector.ZERO, new ShapeGraphics(new Circle(radius), null, Color.RED, 0.005f));
		this.actor2 = new MovingRock(new Vector(0.3f, 0.3f), "Hi!");
		return true;
	}

	@Override
	public void end() {
	}

	@Override
	public String getTitle() {
		return "Demo1";
	}

	@Override
	public void update(float deltaTime) {
		actor1.draw(window);
		actor2.draw(window);

		Keyboard keyboard = window.getKeyboard();
		Button downArrow = keyboard.get(Keyboard.DOWN);
		if (downArrow.isDown()) {
			actor2.update(deltaTime);
		}

		if (actor2.getPosition().sub(actor1.getPosition()).getLength() < radius) {
			(new TextGraphics("BOOM !! ", 0.08f, Color.RED)).draw(window);
		}
	}

	@Override
	public int getFrameRate() {
		return 24;
	}

}
