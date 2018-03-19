package engine.event;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import engine.State;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;

public class EventManager implements DeepCopyable<EventManager>, CopyableAsBase<EventManager> {
	EnumMap<Event, EventHandlers> handlers;

	private EventManager() {
	}
	
	public static EventManager create() {
		EventManager ret = new EventManager();
		ret.handlers = new EnumMap<>(Event.class);
		return ret;
	}

	public EventManager deepCopy() {
		EventManager ret = new EventManager();
		ret.handlers = new EnumMap<>(Event.class);
		for (Entry<Event, EventHandlers> item : handlers.entrySet()) {
			ret.handlers.put(item.getKey(), item.getValue().deepCopy());
		}
		return ret;
	}

	public EventManager copyAsBase() {
		EventManager ret = new EventManager();
		ret.handlers = new EnumMap<>(Event.class);
		for (Map.Entry<Event, EventHandlers> item : handlers.entrySet()) {
			ret.handlers.put(item.getKey(), item.getValue().copyAsBase());
		}
		return ret;
	}

	public int add(Event event, EventHandler handler) {
		return handlers.get(event).add(handler);
	}

	public void markRemoved(Event event, int index) {
		handlers.get(event).markRemoved(index);
	}

	public void invoke(Event event, State state, EventArgument argument) {
		handlers.get(event).invoke(event, state, argument);
	}
}
