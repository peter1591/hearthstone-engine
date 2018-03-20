package engine.event;

import engine.entity.Entity;

public class EventArgument {
	/**
	 * The entity who owns this event handler.
	 */
	final public Entity owner;
	
	/**
	 * Who triggers this event.
	 */
	final public int triggerer;
	
	public int amount;
	
	public EventArgument(Entity owner, int triggerer) {
		this.owner = owner;
		this.triggerer = triggerer;
	}
}
