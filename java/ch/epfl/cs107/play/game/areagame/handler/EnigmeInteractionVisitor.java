package ch.epfl.cs107.play.game.areagame.handler;

import ch.epfl.cs107.play.game.enigme.EnigmeBehavior.EnigmeCell;
import ch.epfl.cs107.play.game.enigme.actor.Collectable;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.game.enigme.actor.Ice;
import ch.epfl.cs107.play.game.enigme.actor.PressurePlate;
import ch.epfl.cs107.play.game.enigme.actor.PressureSwitch;
import ch.epfl.cs107.play.game.enigme.actor.SignalRock;
import ch.epfl.cs107.play.game.enigme.actor.Switchable;
import ch.epfl.cs107.play.game.enigme.actor.TalkingEntity;
import ch.epfl.cs107.play.game.enigme.actor.TeleportStone;

public interface EnigmeInteractionVisitor extends AreaInteractionVisitor {

	/**
	 * Simulate an interaction between an Enigme Interactor and an Enigme
	 * Collectable
	 * 
	 * @param collectable (Collectable), not null
	 */
	default void interactWith(Collectable collectable) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an EnigmeCell
	 * 
	 * @param cell (EnigmeCell), not null
	 */
	default void interactWith(EnigmeCell cell) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an Enigme Door
	 * 
	 * @param door (Door), not null
	 */
	default void interactWith(Door door) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an EnigmePlayer
	 * 
	 * @param player (EnigmePlayer), not null
	 */
	default void interactWith(EnigmePlayer player) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an Enigme Switchable
	 * 
	 * @param switchable (Switchable), not null
	 */
	default void interactWith(Switchable switchable) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an Enigme
	 * PressureSwitch
	 * 
	 * @param pressureSwitch (PressureSwitch), not null
	 */
	default public void interactWith(PressureSwitch pressureSwitch) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an Enigme
	 * PressurePlate
	 * 
	 * @param pressurePlate (PressurePlate), not null
	 */
	default public void interactWith(PressurePlate pressurePlate) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an Enigme SignalRock
	 * 
	 * @param signalRock (SignalRock), not null
	 */
	default public void interactWith(SignalRock signalRock) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an Enigme Ice
	 * 
	 * @param ice (Ice), not null
	 */
	default void interactWith(Ice ice) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an Enigme
	 * TeleportStone
	 * 
	 * @param ts (TeleportStone), not null
	 */
	default void interactWith(TeleportStone ts) {
		// by default the interaction is empty
	}

	/**
	 * Simulate an interaction between an Enigme Interactor and an Enigme
	 * TalkingEntity
	 * 
	 * @param te (TalkingEntity), not null
	 */
	default void interactWith(TalkingEntity te) {
		// by default the interaction is empty
	}
}
