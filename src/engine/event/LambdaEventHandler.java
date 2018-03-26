package engine.event;

import engine.ManagedState;

public final class LambdaEventHandler<T extends EventArgument> implements EventHandler<T> {
	/**
	 * Should be stateless.
	 * 
	 * @author petershih
	 *
	 */
	public static interface Operation<T> {
		boolean handle(ManagedState state, T argument);
	}
	
	protected Operation<T> operation;
	
	protected boolean owned = false;
	
	@Override
	public boolean isOwned() {
		return owned;
	}
	
	@Override
	public void setOwned(boolean owned) {
		this.owned = owned;
	}
	
	protected LambdaEventHandler() {
	}
	
	static public <T extends EventArgument> LambdaEventHandler<T> create(Operation<T> operation) {
		LambdaEventHandler<T> ret = new LambdaEventHandler<T>();
		ret.operation = operation;
		return ret;
	}

	@Override
	public boolean invoke(ManagedState state, T argument) {
		return operation.handle(state, argument);
	}
	
	@Override
	public LambdaEventHandler<T> deepCopy() {
		return this; // this is a stateless object, so it's ok to share the instance
	}
}
