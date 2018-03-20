package engine.event;

import engine.ManagedState;
import engine.utils.DeepCopyable;

public interface EventHandler extends DeepCopyable<EventHandler> {
	/**
	 * 
	 * @param event
	 * @param state
	 * @param argument
	 * @return return true to keep this event handler; false to remove this.
	 */
	boolean invoke(Event event, ManagedState state, EventArgument argument);
	
	/**
	 * Indicates if this event handler is owned by another manager (typically an aura effect).
	 * 
	 * In this case, this owned event handler should not be removed at any situation. The owner
	 * should take care of this.
	 * 
	 * @return Is this event handler owned, or not.
	 */
	boolean isOwned();
	void setOwned(boolean owned);
}
