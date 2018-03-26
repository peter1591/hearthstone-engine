package engine.event;

import engine.entity.Entity;

public class EntityEventArgument extends EventArgument {

	/**
	 * The entity who owns this event handler.
	 */
	public Entity owner;
}
