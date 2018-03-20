package engine.aura;

import engine.State;
import engine.entity.Modifier;
import engine.event.Event;
import engine.event.EventArgument;
import engine.event.LambdaEventHandler;

public interface ToModifyProperty extends AuraSpec {
	Modifier getPropertyModifier();

	default Event getEffectEvent() {
		return Event.PROPERTY_MODIFIERS;
	}

	default LambdaEventHandler createEffectHandler() {
		return LambdaEventHandler.create((Event event, State state, EventArgument argument) -> {
			getPropertyModifier().apply(argument.owner.getMutableProperty());
			return false;
		});
	}
}
