package engine.aura;

import engine.State;
import engine.entity.ReadableProperty;

public interface WhenOwnerDamaged extends AuraSpec {
	default boolean enabled(int auraEmitter, State state) {
		ReadableProperty property = state.getEntityManager().get(auraEmitter).getFinalProperty();
		return property.getHp() < property.getMaxHp();
	}
}