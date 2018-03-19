package engine.event;

import engine.State;
import engine.utils.DeepCopyable;

public interface EventHandler extends DeepCopyable<EventHandler> {
	/**
	 * 
	 * @param event
	 * @param state
	 * @param argument
	 * @return return true to keep this event handler; false to remove this.
	 */
	boolean invoke(Event event, State state, EventArgument argument);
}
