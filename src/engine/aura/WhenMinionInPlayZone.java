package engine.aura;

import engine.State;
import engine.entity.ReadableProperty;
import engine.entity.ReadableProperty.Zone;
import engine.event.EventHandler;

public interface WhenMinionInPlayZone<T extends EventHandler<?>> extends AuraSpec<T> {
	default boolean exists(int auraEmitter, State state) {
		ReadableProperty property = state.getEntityManager().get(auraEmitter).getFinalProperty();
		if (property.getZone() != Zone.PLAY)
			return false;
		if (property.getSilenced())
			return false;
		return true;
	}
}