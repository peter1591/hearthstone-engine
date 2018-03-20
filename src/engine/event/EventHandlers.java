package engine.event;

import engine.ManagedState;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;
import engine.utils.OverlayedArrayList;

class EventHandlers implements DeepCopyable<EventHandlers>, CopyableAsBase<EventHandlers> {
	public static final EventHandler removedHandler = LambdaEventHandler.create(null);

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

	/**
	 * Mark a given event handler as removed. This effectively removes the event handler.
	 * 
	 * However, some event handlers will be marked as 'owned', properly it's occurance is managed
	 * by an aura manager. In these cases, this removal will be failed, and returning false.
	 * 
	 * @param index
	 * @return True if successfully removed; false if event handler is owned, and thus cannot be removed.
	 */
	boolean markRemoved(int index, boolean fromOwner) {
		if (!fromOwner && overlayed_list.get(index).isOwned()) return false;
		
		overlayed_list.set(index, removedHandler);
		return true;
	}

	void invoke(Event event, ManagedState state, EventArgument argument) {
		for (int i = 0; i < overlayed_list.size(); ++i) {
			if (overlayed_list.get(i) == removedHandler)
				continue;
			if (overlayed_list.get(i).invoke(event, state, argument) == false) {
				markRemoved(i, false);
			}
		}
	}
}
