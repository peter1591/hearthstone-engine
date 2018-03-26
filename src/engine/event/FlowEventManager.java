package engine.event;

import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;

/**
 * Manages the events which are belonging to the whole game, rather than
 * belonging to a particular entity.
 * 
 * @author petershih
 *
 */
public class FlowEventManager implements DeepCopyable<FlowEventManager>, CopyableAsBase<FlowEventManager> {
	EventHandlers<EventBeforeZoneChange.Argument> handlersBeforeZoneChange;
	EventHandlers<EventRemovedFromZone.Argument> handlersRemovedFromZone;
	EventHandlers<EventAddedToZone.Argument> handlersAddedToZone;
	
	EventHandlers<EventBeforeTurnEnd.Argument> handlersBeforeTurnEnd;
	EventHandlers<EventAfterTurnStarted.Argument> handlersAfterTurnStarted;

	private FlowEventManager() {
	}
	
	public static FlowEventManager create() {
		FlowEventManager ret = new FlowEventManager();
		ret.handlersBeforeZoneChange = EventHandlers.create();
		ret.handlersRemovedFromZone = EventHandlers.create();
		ret.handlersAddedToZone = EventHandlers.create();
		ret.handlersBeforeTurnEnd = EventHandlers.create();
		ret.handlersAfterTurnStarted = EventHandlers.create();
		return ret;
	}

	public FlowEventManager deepCopy() {
		FlowEventManager ret = new FlowEventManager();
		ret.handlersBeforeZoneChange = handlersBeforeZoneChange.deepCopy();
		ret.handlersRemovedFromZone = handlersRemovedFromZone.deepCopy();
		ret.handlersAddedToZone = handlersAddedToZone.deepCopy();
		ret.handlersBeforeTurnEnd = handlersBeforeTurnEnd.deepCopy();
		ret.handlersAfterTurnStarted = handlersAfterTurnStarted.deepCopy();
		return ret;
	}

	public FlowEventManager copyAsBase() {
		FlowEventManager ret = new FlowEventManager();
		ret.handlersBeforeZoneChange = handlersBeforeZoneChange.copyAsBase();
		ret.handlersRemovedFromZone = handlersRemovedFromZone.copyAsBase();
		ret.handlersAddedToZone = handlersAddedToZone.copyAsBase();
		ret.handlersBeforeTurnEnd = handlersBeforeTurnEnd.copyAsBase();
		ret.handlersAfterTurnStarted = handlersAfterTurnStarted.copyAsBase();
		return ret;
	}
	
	public EventHandlers<EventBeforeZoneChange.Argument> beforeZoneChange() {
		return handlersBeforeZoneChange;
	}
	
	public EventHandlers<EventRemovedFromZone.Argument> removedFromZone() {
		return handlersRemovedFromZone;
	}
	
	public EventHandlers<EventAddedToZone.Argument> addedToZone() {
		return handlersAddedToZone;
	}
	
	public EventHandlers<EventBeforeTurnEnd.Argument> beforeTurnEnd() {
		return handlersBeforeTurnEnd;
	}
	
	public EventHandlers<EventAfterTurnStarted.Argument> afterTurnStarted() {
		return handlersAfterTurnStarted;
	}
}
