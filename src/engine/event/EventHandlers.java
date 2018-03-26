package engine.event;

import engine.ManagedState;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;
import engine.utils.OverlayedArrayList;

public class EventHandlers<T extends EventArgument> implements DeepCopyable<EventHandlers<T>>, CopyableAsBase<EventHandlers<T>> {
	final LambdaEventHandler<T> removedHandler = LambdaEventHandler.create(null);

	OverlayedArrayList<EventHandler<T>> overlayed_list;

	private EventHandlers() {
	}

	public static <T extends EventArgument> EventHandlers<T> create() {
		EventHandlers<T> ret = new EventHandlers<T>();
		ret.overlayed_list = OverlayedArrayList.create();
		return ret;
	}

	public EventHandlers<T> deepCopy() {
		EventHandlers<T> ret = new EventHandlers<T>();
		ret.overlayed_list = overlayed_list.deepCopy();
		return ret;
	}

	public EventHandlers<T> copyAsBase() {
		EventHandlers<T> ret = new EventHandlers<T>();
		ret.overlayed_list = overlayed_list.copyAsBase();
		return ret;
	}

	public int add(EventHandler<T> handler) {
		return overlayed_list.add(handler);
	}

	/**
	 * Mark a given event handler as removed. This effectively removes the event
	 * handler.
	 * 
	 * However, some event handlers will be marked as 'owned', properly it's
	 * occurance is managed by an aura manager. In these cases, this removal will be
	 * failed, and returning false.
	 * 
	 * @param index
	 * @return True if successfully removed; false if event handler is owned, and
	 *         thus cannot be removed.
	 */
	public boolean markRemoved(int index, boolean fromOwner) {
		if (!fromOwner && overlayed_list.get(index).isOwned())
			return false;

		overlayed_list.set(index, removedHandler);
		return true;
	}

	public void invoke(ManagedState state, T argument) {
		for (int i = 0; i < overlayed_list.size(); ++i) {
			if (overlayed_list.get(i) == removedHandler)
				continue;
			if (overlayed_list.get(i).invoke(state, argument) == false) {
				markRemoved(i, false);
			}
		}
	}
}
