package engine.state;

import engine.state.entity.Entity;

public class EntityManipulator {
	State state;
	Entity entity;
	
	public EntityManipulator(State state, Entity entity) {
		this.state = state;
		this.entity = entity;
	}
}
