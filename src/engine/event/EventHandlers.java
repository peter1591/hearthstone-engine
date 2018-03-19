package engine.event;

import engine.State;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;
import engine.utils.OverlayedArrayList;

class EventHandlers implements DeepCopyable<EventHandlers>, CopyableAsBase<EventHandlers> {
	public static final EventHandler removedHandler = EventHandler.createAndRegister("@RemovedHandler", null);

	OverlayedArrayList<EventHandler> overlayed_list;

	private EventHandlers() {
	}

	public static EventHandlers create() {
		EventHandlers ret = new EventHandlers();
		ret.overlayed_list = OverlayedArrayList.create();
		return ret;
	}

	public EventHandlers deepCopy() {
		EventHandlers ret = new EventHandlers();
		ret.overlayed_list = overlayed_list.deepCopy();
		return ret;
	}

	public EventHandlers copyAsBase() {
		EventHandlers ret = new EventHandlers();
		ret.overlayed_list = overlayed_list.copyAsBase();
		return ret;
	}

	int add(EventHandler handler) {
		return overlayed_list.add(handler);
	}

	void remove(int index) {
		overlayed_list.set(index, removedHandler);
	}

	void invoke(Event event, State state, EventArgument argument) {
		for (EventHandler item : overlayed_list) {
			if (item == removedHandler)
				continue;
			item.invoke(event, state, argument);
		}
	}
}
