package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class EnigmeBehavior extends AreaBehavior {

	/**
	 * Initializes the grid by filling it with EnigmeCells.
	 * 
	 * @param window     (Window): display context. Not null
	 * @param fileName (FileSystem): given file system. Not null
	 */
	public EnigmeBehavior(Window window, String fileName) {
		super(window, fileName);
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				setCell(x, y,
						new EnigmeCell(x, y, EnigmeCellType.toType(getBehaviorMap().getRGB(getHeight() - 1 - y, x))));
			}
		}
	}

	/**
	 * Interprets a cell's color as a type.
	 */
	public enum EnigmeCellType {
		NULL(0), WALL(-16777216), // RGB code of black
		DOOR(-65536), // RGB code of red
		WATER(-16776961), // RGB code of blue
		INDOOR_WALKABLE(-1), OUTDOOR_WALKABLE(-14112955);

		final int type;

		EnigmeCellType(int type) {
			this.type = type;
		}

		/**
		 * @param type (int): RGB code
		 * @return (EnigmeCellType): the value corresponding to type
		 */
		static EnigmeCellType toType(int type) {
			switch (type) {
			case 0:
				return NULL;
			case -16777216:
				return WALL;
			case -65536:
				return DOOR;
			case -16776961:
				return WATER;
			case -1:
				return INDOOR_WALKABLE;
			case -14112955:
				return OUTDOOR_WALKABLE;
			default:
				return NULL;
			}
		}
	}

	/**
	 * Enigme's own Cell extension.
	 */
	public class EnigmeCell extends Cell {
		/** The type of the cell */
		private EnigmeCellType type;

		/**
		 * @param x    (int): the x index of the Cell
		 * @param y    (int): the y index of the Cell
		 * @param type (Demo2CellType): the type of the Cell
		 */
		private EnigmeCell(int x, int y, EnigmeCellType type) {
			super(x, y);
			this.type = type;
		}

		@Override
		public boolean isViewInteractable() {
			return false;
		}

		@Override
		public boolean isCellInteractable() {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			return (type != EnigmeCellType.NULL && type != EnigmeCellType.WALL && type != EnigmeCellType.WATER
					&& isTraversable());
		}

		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		public boolean takeCellSpace() {
			return false;
		}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
			super.acceptInteraction(v);
		}

	}
}
