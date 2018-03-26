package engine.aura;

import engine.State;
import engine.entity.ReadableProperty;
import engine.event.EventHandler;

public interface WhenOwnerDamaged<T extends EventHandler<?>> extends AuraSpec<T> {
	default boolean enabled(int auraEmitter, State state) {
		ReadableProperty property = state.getEntityManager().get(auraEmitter).getFinalProperty();
		return property.getHp() < property.getMaxHp();
	}
}