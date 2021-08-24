package ch.epfl.cs107.play.game.areagame;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.epfl.cs107.play.game.Playable;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

/**
 * Area is a "Part" of the AreaGame. It is characterized by its AreaBehavior and
 * a List of Actors
 */
public abstract class Area implements Playable {
	/** Context object */
	private Window window;
	/** Context object */
	private FileSystem fileSystem;
	/** List of Actors inside the area */
	private List<Actor> actors;
	/** Waiting List of Actors to be added */
	private List<Actor> registeredActors;
	/** Waiting List of Actors to be removed */
	private List<Actor> unregisteredActors;
	/** Actor on which the view is centered */
	private Actor viewCandidate;
	/** Effective center of the view */
	private Vector viewCenter;
	/** The behavior Map */
	private AreaBehavior areaBehavior;
	/** Whether the area has been initialized yet */
	private boolean hasBegun = false;
	/** Waiting List of Interactables to be added to the grid */
	private Map<Interactable, List<DiscreteCoordinates>> interactablesToEnter;
	/** Waiting List of Interactables to be removed from the grid */
	private Map<Interactable, List<DiscreteCoordinates>> interactablesToLeave;
	/** List of Interactors inside the area */
	private List<Interactor> interactors;
	/** Whether the area is paused */
	private boolean paused;

	/**
	 * @return (Vector): Effective center of the view
	 */
	public Vector getViewCenter() {
		return viewCenter;
	}

	/**
	 * @return (float): camera scale factor, assume it is the same in x and y
	 *         direction
	 */
	public abstract float getCameraScaleFactor();

	/**
	 * @param a (Actor): actor on which to center the view
	 */
	public final void setViewCandidate(Actor a) {
		this.viewCandidate = a;
	}

	/**
	 * @param ab (AreaBehavior): AreaBehavior to associate to Area
	 */
	protected final void setBehavior(AreaBehavior ab) {
		this.areaBehavior = ab;
	}

	/**
	 * Add an actor to the actors list, moreover if the actor is also an
	 * Interactable and/or an Interactor, it is also added to the corresponding list
	 * 
	 * @param a      (Actor): the actor to add, not null
	 * @param forced (Boolean): if true, the method ends
	 */
	private void addActor(Actor a, boolean forced) {
		boolean errorOccured = !actors.add(a);

		if (a instanceof Interactable)
			errorOccured = errorOccured || !enterAreaCells(((Interactable) a), ((Interactable) a).getCurrentCells());

		if (a instanceof Interactor)
			errorOccured = errorOccured || !interactors.add((Interactor) a);

		if (errorOccured && !forced) {
			System.out.println("Actor " + a + " cannot be completely added, so remove it from where it was");
			removeActor(a, true);
		}
	}

	/**
	 * Remove an actor from the actor list, moreover if the actor is also an
	 * Interactable and/or an Interactor, it is also removed from the corresponding
	 * list
	 * 
	 * @param a      (Actor): the actor to remove, not null
	 * @param forced (Boolean): if true, the method ends
	 */
	private void removeActor(Actor a, boolean forced) {
		boolean errorOccured = !actors.remove(a);

		if (a instanceof Interactable)
			errorOccured = errorOccured || !leaveAreaCells(((Interactable) a), ((Interactable) a).getCurrentCells());

		if (a instanceof Interactor)
			errorOccured = errorOccured || !interactors.remove((Interactor) a);

		if (errorOccured && !forced) {
			System.out.println("Actor " + a + " cannot be completely removed, so add it to where it was");
			addActor(a, true);
		}
	}

	/**
	 * Register an actor : will be added at next update
	 * 
	 * @param a (Actor): the actor to register, not null
	 * @return (boolean): true if the actor is correctly registered
	 */
	public final boolean registerActor(Actor a) {
		return registeredActors.add(a);
	}

	/**
	 * Unregister an actor : will be removed at next update
	 * 
	 * @param a (Actor): the actor to unregister, not null
	 * @return (boolean): true if the actor is correctly unregistered
	 */
	public final boolean unregisterActor(Actor a) {
		return unregisteredActors.add(a);
	}

	/**
	 * Updates list of actors and grid, adding all registered actors and removing
	 * all unregistered actors, then clears waiting lists.
	 */
	private final void purgeRegistration() {
		for (Actor a : registeredActors)
			addActor(a, false);

		for (Actor a : unregisteredActors)
			removeActor(a, false);

		registeredActors.clear();
		unregisteredActors.clear();

		for (Entry<Interactable, List<DiscreteCoordinates>> entry : interactablesToEnter.entrySet()) {
			areaBehavior.enter(entry.getKey(), entry.getValue());
		}

		for (Entry<Interactable, List<DiscreteCoordinates>> entry : interactablesToLeave.entrySet()) {
			areaBehavior.leave(entry.getKey(), entry.getValue());
		}

		interactablesToEnter.clear();
		interactablesToLeave.clear();
	}

	/**
	 * Getter for the area width
	 * 
	 * @return (int) : the width in number of cols
	 */
	public final int getWidth() {
		return areaBehavior.getWidth();
	}

	/**
	 * Getter for the area height
	 * 
	 * @return (int) : the height in number of rows
	 */
	public final int getHeight() {
		return areaBehavior.getHeight();
	}

	/** @return the Window Keyboard for inputs */
	public final Keyboard getKeyboard() {
		return window.getKeyboard();
	}

	/**
	 * @return (boolean): whether the area has been initialized yet
	 */
	public boolean hasBegun() {
		return hasBegun;
	}

	/**
	 * Centers camera view on candidate if it exists, else centers view on origin
	 */
	private void updateCamera() {
		if (viewCandidate != null) {
			viewCenter = viewCandidate.getPosition();
			Transform viewTransform = Transform.I.scaled(getCameraScaleFactor()).translated(viewCenter);
			window.setRelativeTransform(viewTransform);
		} else {
			Transform viewTransform = Transform.I.scaled(getCameraScaleFactor()).translated(Vector.ZERO);
			window.setRelativeTransform(viewTransform);
		}
	}

	/**
	 * Suspend method: Can be overridden, called before resume other
	 */
	public void suspend() {
		purgeRegistration();
	}

	/**
	 * Checks if the grid associated to the area allows entity to leave the grid. If
	 * that is the case, entity is registered as leaving.
	 * 
	 * @param entity      (Interactable): the entity wanting to leave the grid
	 * @param coordinates (DiscreteCoordinates): the coordinates which the entity
	 *                    wants to leave
	 * @return (boolean): true if the grid allows the entity to leave the
	 *         coordinates
	 */
	public final boolean leaveAreaCells(Interactable entity, List<DiscreteCoordinates> coordinates) {
		if (areaBehavior.canLeave(entity, coordinates)) {
			interactablesToLeave.put(entity, coordinates);
			return true;
		}
		return false;
	}

	/**
	 * Checks if the grid associated to the area allows entity to enter the grid. If
	 * that is the case, entity is registered as entering.
	 * 
	 * @param entity      (Interactable): the entity wanting to enter the grid
	 * @param coordinates (DiscreteCoordinates): the coordinates which the entity
	 *                    wants to enter
	 * @return (boolean): true if the grid allows the entity to enter the
	 *         coordinates
	 */
	public final boolean enterAreaCells(Interactable entity, List<DiscreteCoordinates> coordinates) {
		if (areaBehavior.canEnter(entity, coordinates)) {
			interactablesToEnter.put(entity, coordinates);
			return true;
		}
		return false;
	}

	/**
	 * Resume method: Can be overridden
	 * 
	 * @param window     (Window): display context, not null
	 * @param fileSystem (FileSystem): given file system, not null
	 * @return (boolean) : if the resume succeed, true by default
	 */
	public boolean resume(Window window, FileSystem fileSystem) {
		return true;
	}

	/// Area implements Playable

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		this.fileSystem = fileSystem;
		this.actors = new LinkedList<>();
		this.registeredActors = new LinkedList<>();
		this.unregisteredActors = new LinkedList<>();
		this.viewCandidate = null;
		this.viewCenter = Vector.ZERO;
		this.hasBegun = true;
		this.interactablesToEnter = new HashMap<Interactable, List<DiscreteCoordinates>>();
		this.interactablesToLeave = new HashMap<Interactable, List<DiscreteCoordinates>>();
		this.interactors = new LinkedList<>();
		return true;
	}

	@Override
	public void update(float deltaTime) {
		Button pKey = getKeyboard().get(Keyboard.P);

		if (pKey.isPressed()) {
			paused = !paused;
		}
		if (!paused) {
			purgeRegistration();

			for (Actor actor : actors)
				actor.update(deltaTime);

			for (Interactor interactor : interactors) {
				if (interactor.wantsCellInteraction()) {
					areaBehavior.cellInteractionOf(interactor);
				}
				if (interactor.wantsViewInteraction()) {
					areaBehavior.viewInteractionOf(interactor);
				}
			}

			updateCamera();

		} else {
			TextGraphics text1 = new TextGraphics("PAUSE", 2f, Color.BLACK, null, 0.0f, false, false,
					viewCenter.sub(4, 0), 2);
			TextGraphics text2 = new TextGraphics("Press P to resume", 0.5f, Color.BLACK, null, 0.0f, false, false,
					viewCenter.sub(3, 0.5f), 2);
			text1.draw(window);
			text2.draw(window);
		}

		for (Actor actor : actors) {
			actor.draw(window);
		}
	}

	@Override
	public void end() {
		// TODO save the AreaState somewhere
	}
}
