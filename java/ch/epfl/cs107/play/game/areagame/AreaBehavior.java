package ch.epfl.cs107.play.game.areagame;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;

/**
 * AreaBehavior manages a map of Cells.
 */
public abstract class AreaBehavior {

	/** The behavior is an Image of size height x width */
	private final Image behaviorMap;
	/** The behaviorMap's width */
	private final int width;
	/** The behaviorMap's height */
	private final int height;
	/** An array of cells representing the behavior map */
	private final Cell[][] cells;

	/**
	 * Default AreaBehavior Constructor
	 * 
	 * @param window   (Window): graphic context, not null
	 * @param fileName (String): name of the file containing the behavior image, not
	 *                 null
	 */
	public AreaBehavior(Window window, String fileName) {
		this.behaviorMap = window.getImage(ResourcePath.getBehaviors(fileName), null, false);
		this.width = behaviorMap.getWidth();
		this.height = behaviorMap.getHeight();
		this.cells = new Cell[width][height];
	}

	/** @return (int) : the width in number of cols */
	public int getWidth() {
		return width;
	}

	/** @return (int) : the height in number of rows */
	public int getHeight() {
		return height;
	}

	/** @return (Image) : the behavior map */
	public Image getBehaviorMap() {
		return behaviorMap;
	}

	/**
	 * Sets a cell in the cells array.
	 * 
	 * @param x    (int): the cell's x index in the array
	 * @param y    (int): the cell's y index in the array
	 * @param cell (Cell): the Cell to put in the array
	 */
	public void setCell(int x, int y, Cell cell) {
		cells[x][y] = cell;
	}

	/**
	 * @param x (int): the x index of the Cell
	 * @param y (int): the y index of the Cell
	 * @return (Cell): the Cell at position (x,y)
	 */
	public Cell getCell(int x, int y) {
		return cells[x][y];
	}

	/**
	 * Checks if the grid agrees to the entity leaving certain coordinates.
	 * 
	 * @param entity      (Interactable): the entity which wishes to leave the
	 *                    certain coordinates
	 * @param coordinates (DiscreteCoordinates): the coordinates which the entity
	 *                    wishes to leave
	 * @return (boolean): whether the grid has agreed to the entity leaving the
	 *         coordinates
	 */
	public boolean canLeave(Interactable entity, List<DiscreteCoordinates> coordinates) {
		for (DiscreteCoordinates coord : entity.getCurrentCells()) {
			if (!cells[coord.x][coord.y].canLeave(entity))
				return false;
		}
		return true;
	}

	/**
	 * 
	 * Checks if the grid agrees to the entity entering certain coordinates.
	 * 
	 * @param entity      (Interactable): the entity which wishes to enter the
	 *                    certain coordinates
	 * @param coordinates (DiscreteCoordinates): the coordinates which the entity
	 *                    wishes to enter
	 * @return (boolean): whether the grid has agreed to the entity entering the
	 *         coordinates
	 */
	public boolean canEnter(Interactable entity, List<DiscreteCoordinates> coordinates) {
		if (coordinates.isEmpty())
			return false;
		for (DiscreteCoordinates coord : coordinates) {
			int x = coord.x;
			int y = coord.y;
			if (!cells[x][y].canEnter(entity) || x < 0 || y < 0 || x >= width || y >= height)
				return false;
		}
		return true;
	}

	/**
	 * Deletes the entity from all the coordinates.
	 * 
	 * @param entity      (Interactable): the entity which wishes to leave the
	 *                    certain coordinates
	 * @param coordinates (DiscreteCoordinates): the coordinates which the entity
	 *                    wishes to leave
	 */
	protected void leave(Interactable entity, List<DiscreteCoordinates> coordinates) {
		for (DiscreteCoordinates coord : coordinates) {
			cells[coord.x][coord.y].leave(entity);
		}
	}

	/**
	 * Adds the entity to all the coordinates.
	 * 
	 * @param entity      (Interactable): the entity which wishes to enter the
	 *                    certain coordinates
	 * @param coordinates (DiscreteCoordinates): the coordinates which the entity
	 *                    wishes to enter
	 */
	protected void enter(Interactable entity, List<DiscreteCoordinates> coordinates) {
		for (DiscreteCoordinates coord : coordinates) {
			cells[coord.x][coord.y].enter(entity);
		}
	}

	/**
	 * The Interactor cellInteracts with each of the Interactables at its current
	 * position.
	 * 
	 * @param interactor (Interactor): wants to cellInteract with Interactables
	 */
	public void cellInteractionOf(Interactor interactor) {
		for (DiscreteCoordinates coord : interactor.getCurrentCells()) {
			cells[coord.x][coord.y].cellInteractionOf(interactor);
		}
	}

	/**
	 * The Interactor viewInteracts with each of the Interactables in its field of
	 * view.
	 * 
	 * @param interactor (Interactor): wants to viewInteract with Interactables
	 */
	public void viewInteractionOf(Interactor interactor) {
		for (DiscreteCoordinates coord : interactor.getFieldOfViewCells()) {
			cells[coord.x][coord.y].viewInteractionOf(interactor);
		}
	}

	/**
	 * Each game will have its own Cell extension.
	 */
	public abstract class Cell implements Interactable {
		/** The cell's coordinates on the grid */
		private DiscreteCoordinates coordinates;
		/** The cell's contents */
		private Set<Interactable> entities;

		/**
		 * Creates a new cell.
		 * 
		 * @param y (int): The row index
		 * @param x (int): The column index
		 */
		public Cell(int x, int y) {
			this.coordinates = new DiscreteCoordinates(x, y);
			this.entities = new HashSet<>();
		}

		/**
		 * Adds i to the Cell's contents.
		 * 
		 * @param i (Interactable): interactable which needs to be added to the cell's
		 *          contents
		 */
		private void enter(Interactable i) {
			entities.add(i);
		}

		/**
		 * Deletes i from the Cell's contents.
		 * 
		 * @param i (Interactable): interactable which needs to be removed from the cell's
		 *          contents
		 * 
		 */
		private void leave(Interactable i) {
			entities.remove(i);
		}

		/**
		 * @param entity (Interactable): the entity which wishes to leave the Cell
		 * @return (boolean): whether the entity can leave the Cell
		 */
		protected abstract boolean canLeave(Interactable entity);

		/**
		 * @param entity (Interactable): the entity which wishes to enter the Cell
		 * @return (boolean): whether the entity can enter the Cell
		 */
		protected abstract boolean canEnter(Interactable entity);

		/**
		 * Checks if the cell is traversable, that is if no entities are taking the cell
		 * space.
		 * 
		 * @return (boolean): whether no entity is making the Cell untraversable
		 */
		protected boolean isTraversable() {
			for (Interactable i : entities) {
				if (i.takeCellSpace())
					return false;
			}
			return true;
		}

		/**
		 * The Interactor cellInteracts with each of the entities in the cell.
		 * 
		 * @param interactor (Interactor): wants to cellInteract with Interactables
		 */
		private void cellInteractionOf(Interactor interactor) {
			for (Interactable interactable : entities) {
				if (interactable.isCellInteractable())
					interactor.interactWith(interactable);
			}
		}

		/**
		 * The Interactor viewInteracts with each of the entities in the cell.
		 * 
		 * @param interactor (Interactor): wants to viewInteract with Interactables
		 */
		private void viewInteractionOf(Interactor interactor) {
			for (Interactable interactable : entities) {
				if (interactable.isViewInteractable())
					interactor.interactWith(interactable);
			}
		}

		@Override
		public List<DiscreteCoordinates> getCurrentCells() {
			return Collections.singletonList(coordinates);
		}
	}
}
