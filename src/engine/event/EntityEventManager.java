package engine.event;

import engine.event.EventRemovedFromZone;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;

public class EntityEventManager implements DeepCopyable<EntityEventManager>, CopyableAsBase<EntityEventManager> {
	EventHandlers<EventCheckPlayable.Argument> handlersCheckPlayable;
	EventHandlers<EventOnPlay.Argument> handlersOnPlay;
	
	EventHandlers<EventRemovedFromZone.Argument> handlersRemovedFromZone;
	EventHandlers<EventAddedToZone.Argument> handlersAddedToZone;
	
	EventHandlers<EventModifyProperty.Argument> handlersModifyProperty;
	
	EventHandlers<EventAfterAttacked.Argument> handlersAfterAttacked;
	EventHandlers<EventOnDamaged.Argument> handlersOnDamaged;

	private EntityEventManager() {
	}
	
	public static EntityEventManager create() {
		EntityEventManager ret = new EntityEventManager();
		ret.handlersCheckPlayable = EventHandlers.create();
		ret.handlersOnPlay = EventHandlers.create();
		
		ret.handlersRemovedFromZone = EventHandlers.create();
		ret.handlersAddedToZone = EventHandlers.create();
		
		ret.handlersModifyProperty = EventHandlers.create();
		
		ret.handlersAfterAttacked = EventHandlers.create();
		ret.handlersOnDamaged = EventHandlers.create();
		
		return ret;
	}

	public EntityEventManager deepCopy() {
		EntityEventManager ret = new EntityEventManager();
		ret.handlersCheckPlayable = handlersCheckPlayable.deepCopy();
		ret.handlersOnPlay = handlersOnPlay.deepCopy();
		
		ret.handlersRemovedFromZone = handlersRemovedFromZone.deepCopy();
		ret.handlersAddedToZone = handlersAddedToZone.deepCopy();
		
		ret.handlersModifyProperty = handlersModifyProperty.deepCopy();
		
		ret.handlersAfterAttacked = handlersAfterAttacked.deepCopy();
		ret.handlersOnDamaged = handlersOnDamaged.deepCopy();
		
		return ret;
	}

	public EntityEventManager copyAsBase() {
		EntityEventManager ret = new EntityEventManager();
		ret.handlersCheckPlayable = handlersCheckPlayable.copyAsBase();
		ret.handlersOnPlay = handlersOnPlay.copyAsBase();
		
		ret.handlersRemovedFromZone = handlersRemovedFromZone.copyAsBase();
		ret.handlersAddedToZone = handlersAddedToZone.copyAsBase();
		
		ret.handlersModifyProperty = handlersModifyProperty.copyAsBase();
		
		ret.handlersAfterAttacked = handlersAfterAttacked.copyAsBase();
		ret.handlersOnDamaged = handlersOnDamaged.copyAsBase();
		
		return ret;
	}
	
	public EventHandlers<EventCheckPlayable.Argument> checkPlayable() {
		return handlersCheckPlayable;
	}
	
	public EventHandlers<EventOnPlay.Argument> onPlay() {
		return handlersOnPlay;
	}
	
	public EventHandlers<EventRemovedFromZone.Argument> removedFromZone() {
		return handlersRemovedFromZone;
	}
	
	public EventHandlers<EventAddedToZone.Argument> addedToZone() {
		return handlersAddedToZone;
	}
	
	public EventHandlers<EventModifyProperty.Argument> modifyProperty() {
		return handlersModifyProperty;
	}
	
	public EventHandlers<EventAfterAttacked.Argument> afterAttacked() {
		return handlersAfterAttacked;
	}
	
	public EventHandlers<EventOnDamaged.Argument> onDamaged() {
		return handlersOnDamaged;
	}
}
