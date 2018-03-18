package engine.event;

import engine.State;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;
import engine.utils.OverlayedArrayList;

class Handlers implements DeepCopyable<Handlers>, CopyableAsBase<Handlers> {
	public static final Handler removedHandler = Handler.createAndRegister("@RemovedHandler", null);

	OverlayedArrayList<Handler> overlayed_list;

	private Handlers() {
	}

	public static Handlers create() {
		Handlers ret = new Handlers();
		ret.overlayed_list = OverlayedArrayList.create();
		return ret;
	}

	public Handlers deepCopy() {
		Handlers ret = new Handlers();
		ret.overlayed_list = overlayed_list.deepCopy();
		return ret;
	}

	public Handlers copyAsBase() {
		Handlers ret = new Handlers();
		ret.overlayed_list = overlayed_list.copyAsBase();
		return ret;
	}

	int add(Handler handler) {
		return overlayed_list.add(handler);
	}

	void remove(int index) {
		overlayed_list.set(index, removedHandler);
	}

	void invoke(Event event, State state, EventArgument argument) {
		for (Handler item : overlayed_list) {
			if (item == removedHandler)
				continue;
			item.invoke(event, state, argument);
		}
	}
}
