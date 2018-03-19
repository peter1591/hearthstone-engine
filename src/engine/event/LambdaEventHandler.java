package engine.event;

import engine.State;

public class LambdaEventHandler implements EventHandler {
	/**
	 * Should be stateless.
	 * 
	 * @author petershih
	 *
	 */
	public interface Operation {
		boolean handle(Event event, State state, EventArgument argument);
	}
	
	protected Operation operation;
	
	protected LambdaEventHandler() {
	}
	
	static public LambdaEventHandler create(Operation operation) {
		LambdaEventHandler ret = new LambdaEventHandler();
		ret.operation = operation;
		return ret;
	}

	@Override
	public boolean invoke(Event event, State state, EventArgument argument) {
		return operation.handle(event, state, argument);
	}
	
	@Override
	public LambdaEventHandler deepCopy() {
		return this; // this is a stateless object, so it's ok to share the instance
	}
}
