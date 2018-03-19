package engine.aura;

import engine.State;
import engine.entity.ReadableProperty;
import engine.entity.ReadableProperty.Zone;

public interface WhenMinionInPlayZone extends AuraUpdaterSpec {
	default boolean exists(int auraEmitter, State state) {
		ReadableProperty property = state.getEntityManager().get(auraEmitter).getFinalProperty();
		if (property.getZone() != Zone.PLAY)
			return false;
		if (property.getSilenced())
			return false;
		return true;
	}
}