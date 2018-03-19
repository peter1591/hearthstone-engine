package engine.aura;

import engine.State;
import engine.entity.Modifier;
import engine.event.Event;
import engine.event.EventArgument;
import engine.event.EventHandler;
import engine.event.LambdaEventHandler;

public interface ToModifyProperty extends AuraUpdaterSpec {
	Modifier getPropertyModifier();

	default Event getEffectEvent() {
		return Event.AURA_PROPERTY_MODIFIERS;
	}

	default EventHandler createEffectHandler() {
		return LambdaEventHandler.create((Event event, State state, EventArgument argument) -> {
			getPropertyModifier().apply(argument.owner.getMutableProperty());
			return false;
		});
	}
}
