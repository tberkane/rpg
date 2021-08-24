package ch.epfl.cs107.play.game.enigme;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Demo2Behavior extends AreaBehavior {

	/**
	 * Initializes the grid by filling it with Demo2Cells.
	 * 
	 * @param window     (Window): display context. Not null
	 * @param fileName (FileSystem): given file system. Not null
	 */
	public Demo2Behavior(Window window, String fileName) {
		super(window, fileName);
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				setCell(x, y,
						new Demo2Cell(x, y, Demo2CellType.toType(getBehaviorMap().getRGB(getHeight() - 1 - y, x))));
			}
		}
	}

	/**
	 * 
	 * @param coordinates (List): the coordinates to check for
	 *                    doors
	 * @return (boolean): whether one of the cells in coordinates is a door
	 */
	public boolean isDoor(List<DiscreteCoordinates> coordinates) {
		for (DiscreteCoordinates coord : coordinates) {
			int x = coord.x;
			int y = coord.y;
			if (((Demo2Cell) getCell(x, y)).isDoor())
				return true;
		}
		return false;
	}

	/**
	 * Interprets a cell's color as a type.
	 */
	public enum Demo2CellType {
		NULL(0), WALL(-16777216), // RGB code of black
		DOOR(-65536), // RGB code of red
		WATER(-16776961), // RGB code of blue
		INDOOR_WALKABLE(-1), OUTDOOR_WALKABLE(-14112955);

		final int type;

		Demo2CellType(int type) {
			this.type = type;
		}

		/**
		 * @param type (int): RGB code
		 * @return (Demo2CellType): the value corresponding to type
		 */
		static Demo2CellType toType(int type) {
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
	 * Demo2's own Cell extension.
	 */
	public class Demo2Cell extends Cell {
		/** The type of the cell */
		private Demo2CellType type;

		/**
		 * @param x    (int): the x index of the Cell
		 * @param y    (int): the y index of the Cell
		 * @param type (Demo2CellType): the type of the Cell
		 */
		private Demo2Cell(int x, int y, Demo2CellType type) {
			super(x, y);
			this.type = type;
		}

		/**
		 * @return (boolean): whether this Cell's type is DOOR
		 */
		protected boolean isDoor() {
			return type == Demo2CellType.DOOR;
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
			return (type != Demo2CellType.NULL && type != Demo2CellType.WALL);
		}

		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		public boolean takeCellSpace() {
			return false;
		}

	}
}
