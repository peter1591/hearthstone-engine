package engine.aura;

import engine.ManagedState;
import engine.entity.Modifier;
import engine.event.EntityEventManager;
import engine.event.EventHandler;
import engine.event.EventModifyProperty;
import engine.event.LambdaEventHandler;

public interface ToModifyProperty extends AuraSpec<EventHandler<EventModifyProperty.Argument>> {
	Modifier getPropertyModifier();

	@Override
	default int addEvent(EntityEventManager eventManager, int targetEntity) {
		return eventManager.modifyProperty()
				.add(LambdaEventHandler.create((ManagedState state, EventModifyProperty.Argument argument) -> {
					getPropertyModifier().apply(argument.owner.getMutableProperty());
					return false;
				}));
	}
}
