package engine.state.event;

import engine.state.entity.EntityManager;

public class EventArgument {
	public int source = EntityManager.UNKNOWN_ENTITY_ID;
	public int target = EntityManager.UNKNOWN_ENTITY_ID;
}
