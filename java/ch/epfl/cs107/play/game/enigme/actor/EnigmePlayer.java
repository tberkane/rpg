package ch.epfl.cs107.play.game.enigme.actor;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class EnigmePlayer extends MovableAreaEntity implements Interactor {
	/** Whether the player is about to enter a door */
	private boolean isEnteringDoor;
	/** The last door which the player has passed through */
	private Door lastPassedDoor;
	/** Animation duration in number of frames */
	private final static int ANIMATION_DURATION = 8;
	/** Handles interactions between the player and Interactables */
	private final EnigmePlayerHandler handler;
	/** The animation for when the player is facing down */
	private Animation animationDOWN;
	/** The animation for when the player is facing left */
	private Animation animationLEFT;
	/** The animation for when the player is facing up */
	private Animation animationUP;
	/** The animation for when the player is facing right */
	private Animation animationRIGHT;
	/** Whether the player is in running mode */
	private boolean running;
	/**
	 * Displays the dialogs which the player will trigger through interactions for
	 * example
	 */
	private Dialog dialog;
	/** Whether the dialog window should be shown */
	private boolean showDialog;

	/**
	 * Creates a new EnigmePlayer.
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param coordinates (DiscreteCoordinates): Initial position of the player. Not
	 *                    null
	 */
	public EnigmePlayer(Area area, DiscreteCoordinates coordinates) {
		this(area, Orientation.DOWN, coordinates);
	}

	/**
	 * Creates a new EnigmePlayer.
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param orientation (Orientation): Initial orientation of the player. Not null
	 * @param coordinates (DiscreteCoordinates): Initial position of the player. Not
	 *                    null
	 */
	public EnigmePlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates);
		this.handler = new EnigmePlayerHandler();
		this.dialog = new Dialog("", "dialog.1", getOwnerArea());
		this.showDialog = false;

		// The sprite sheet from which we will extract each individual sprite for the
		// animation
		String spriteSheetName = "max.new.1";

		// Parameters which will be passed to the sprites when they are initialized
		Vector anchor = new Vector(0.25f / 2, 0.32f / 2);
		float f = 1.5f;
		float w = 0.5f * f;
		float h = 0.65625f * f;

		// The 4 sprites for when the player is immobile
		Sprite immobileSpriteDOWN = new Sprite(spriteSheetName, w, h, this, new RegionOfInterest(0, 0, 16, 21), anchor);
		Sprite immobileSpriteLEFT = new Sprite(spriteSheetName, w, h, this, new RegionOfInterest(16, 0, 16, 21),
				anchor);
		Sprite immobileSpriteUP = new Sprite(spriteSheetName, w, h, this, new RegionOfInterest(32, 0, 16, 21), anchor);
		Sprite immobileSpriteRIGHT = new Sprite(spriteSheetName, w, h, this, new RegionOfInterest(48, 0, 16, 21),
				anchor);

		// 4 arrays for the sprites for when the player is in movement
		Sprite[] movingSpritesDOWN = new Sprite[3];
		Sprite[] movingSpritesLEFT = new Sprite[3];
		Sprite[] movingSpritesUP = new Sprite[3];
		Sprite[] movingSpritesRIGHT = new Sprite[3];

		// Filling the 4 arrays with all the sprites
		for (int i = 0; i <= 2; i++) {
			movingSpritesDOWN[i] = new Sprite(spriteSheetName, w, h, this,
					new RegionOfInterest(0, (i + 1) * 21, 16, 21), anchor);
			movingSpritesLEFT[i] = new Sprite(spriteSheetName, w, h, this,
					new RegionOfInterest(16, (i + 1) * 21, 16, 21), anchor);
			movingSpritesUP[i] = new Sprite(spriteSheetName, w, h, this, new RegionOfInterest(32, (i + 1) * 21, 16, 21),
					anchor);
			movingSpritesRIGHT[i] = new Sprite(spriteSheetName, w, h, this,
					new RegionOfInterest(48, (i + 1) * 21, 16, 21), anchor);
		}

		// Creating the actual Animation objects
		this.animationDOWN = new Animation(immobileSpriteDOWN, movingSpritesDOWN);
		this.animationLEFT = new Animation(immobileSpriteLEFT, movingSpritesLEFT);
		this.animationUP = new Animation(immobileSpriteUP, movingSpritesUP);
		this.animationRIGHT = new Animation(immobileSpriteRIGHT, movingSpritesRIGHT);
	}

	/**
	 * Allows the player to enter an area at a certain position.
	 * 
	 * @param area     (Area): the area to enter
	 * @param position (DiscreteCoordinates): the position the player starts at in
	 *                 the given area
	 */
	public void enterArea(Area area, DiscreteCoordinates position) {
		area.registerActor(this);
		setOwnerArea(area);
		this.setCurrentPosition(position.toVector());
		resetMotion();
	}

	/**
	 * Allows the player to leave its current area.
	 */
	public void leaveArea() {
		getOwnerArea().unregisterActor(this);
	}

	/**
	 * @param isEnteringDoor (boolean): whether the player is about to enter a door
	 */
	public void setEnteringDoor(boolean isEnteringDoor) {
		this.isEnteringDoor = isEnteringDoor;
	}

	/**
	 * @return (boolean): whether the player is about to enter a door
	 */
	public boolean isEnteringDoor() {
		return isEnteringDoor;
	}

	/**
	 * Indicates the player is about to pass a door.
	 * 
	 * @param door (Door): the door which the player is about to enter
	 */
	private void setIsPassingDoor(Door door) {
		isEnteringDoor = true;
		lastPassedDoor = door;
	}

	/** @return (Door): the last door which the player has passed through */
	public Door passedDoor() {
		return lastPassedDoor;
	}

	@Override
	public boolean takeCellSpace() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		// Draws the player's animation depending on its orientation, whether it is
		// moving and if it is running
		switch (getOrientation()) {
		case DOWN:
			animationDOWN.draw(canvas, isMoving(), running);
			break;
		case LEFT:
			animationLEFT.draw(canvas, isMoving(), running);
			break;
		case UP:
			animationUP.draw(canvas, isMoving(), running);
			break;
		case RIGHT:
			animationRIGHT.draw(canvas, isMoving(), running);
			break;
		}

		// Displays dialog window if it has to be shown
		if (showDialog) {
			dialog.draw(canvas);
		}
	}

	@Override
	public void update(float deltaTime) {
		// Initialization of all control keys
		Keyboard keyboard = getOwnerArea().getKeyboard();
		Button downArrow = keyboard.get(Keyboard.DOWN);
		Button upArrow = keyboard.get(Keyboard.UP);
		Button leftArrow = keyboard.get(Keyboard.LEFT);
		Button rightArrow = keyboard.get(Keyboard.RIGHT);
		Button rKey = keyboard.get(Keyboard.R);
		Button spaceBar = keyboard.get(Keyboard.SPACE);

		// Space bar scrolls through dialogues, and if there is nothing more to scroll
		// through, the dialogue window is closed
		if (spaceBar.isPressed()) {
			if (dialog.push())
				showDialog = false;
		}

		// R key toggles running mode on and off
		if (rKey.isPressed()) {
			running = !running;
		}

		// Modifies the movement speed depending on if the player is running
		int animationDuration = (running ? ANIMATION_DURATION / 2 : ANIMATION_DURATION);

		if (downArrow.isDown()) {
			if (getOrientation() == Orientation.DOWN) {
				move(animationDuration);
			} else {
				setOrientation(Orientation.DOWN);
			}
		}
		if (upArrow.isDown()) {
			if (getOrientation() == Orientation.UP) {
				move(animationDuration);
			} else {
				setOrientation(Orientation.UP);
			}
		}
		if (leftArrow.isDown()) {
			if (getOrientation() == Orientation.LEFT) {
				move(animationDuration);
			} else {
				setOrientation(Orientation.LEFT);
			}
		}
		if (rightArrow.isDown()) {
			if (getOrientation() == Orientation.RIGHT) {
				move(animationDuration);
			} else {
				setOrientation(Orientation.RIGHT);
			}
		}
		super.update(deltaTime);
	}

	/** Handles interactions between the player and Interactables */
	private class EnigmePlayerHandler implements EnigmeInteractionVisitor {
		@Override
		public void interactWith(Door door) {
			setIsPassingDoor(door);
		}

		@Override
		public void interactWith(TeleportStone teleportStone) {
			teleportStone.collect();
			setCurrentPosition(teleportStone.getArrivalCoordinates().toVector());
			dialog.resetDialog("You found a Teleport Stone!");
			showDialog = true;
		}

		@Override
		public void interactWith(Collectable collectable) {
			collectable.collect();
		}

		@Override
		public void interactWith(PressureSwitch pressureSwitch) {
			if (isMoving() && getCurrentMainCellCoordinates().equals(getTargetMainCellCoordinates()))
				pressureSwitch.toggle();
		}

		@Override
		public void interactWith(PressurePlate pressurePlate) {
			pressurePlate.toggle();
		}

		@Override
		public void interactWith(Switchable switchable) {
			switchable.toggle();
		}

		@Override
		public void interactWith(SignalRock sr) {
			// If the player ever gets stuck on top of a rock, try moving it and if it
			// doesn't work, rotate his orientation until it does
			if (!isMoving() && !move(ANIMATION_DURATION / 4)) {
				setOrientation(getOrientation().hisRight());
			}
		}

		@Override
		public void interactWith(Ice ice) {
			// Ice makes the player slip
			move(ANIMATION_DURATION / 2);
		}

		@Override
		public void interactWith(TalkingEntity te) {
			dialog.resetDialog(te.getMessage());
			showDialog = true;
		}

	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return getEnteringCells();
	}

	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		Keyboard keyboard = getOwnerArea().getKeyboard();
		Button lKey = keyboard.get(Keyboard.L);
		return (lKey.isPressed());
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
	}
}
