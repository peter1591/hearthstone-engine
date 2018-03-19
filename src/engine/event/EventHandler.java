package engine.event;

import engine.State;
import engine.utils.DeepCopyable;

public final class EventHandler implements DeepCopyable<EventHandler> {
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
	
	private EventHandler() {
	}
	
	static public EventHandler createAndRegister(String name, Operation operation) {
		EventHandler ret = new EventHandler();
		ret.operation = operation;
		
		HandlerRegister.getInstance().register(name, ret);
		
		return ret;
	}
	
	public void invoke(Event event, State state, EventArgument argument) {
		operation.handle(event, state, argument);
	}
	
	@Override
	public EventHandler deepCopy() {
		return this; // this is a stateless object, so it's ok to share the instance
	}
}
