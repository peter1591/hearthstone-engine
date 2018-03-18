package engine.event;

import engine.State;
import engine.utils.DeepCopyable;

public final class Handler implements DeepCopyable<Handler> {
	/**
	 * Should be stateless.
	 * 
	 * @author petershih
	 *
	 */
	public interface Operation {
		void handle(Event event, State state, EventArgument argument);
	}
	
	Operation operation;
	
	private Handler() {
	}
	
	static public Handler createAndRegister(String name, Operation operation) {
		Handler ret = new Handler();
		ret.operation = operation;
		
		HandlerRegister.getInstance().register(name, ret);
		
		return ret;
	}
	
	public void invoke(Event event, State state, EventArgument argument) {
		operation.handle(event, state, argument);
	}
	
	@Override
	public Handler deepCopy() {
		return this; // this is a stateless object, so it's ok to share the instance
	}
}
