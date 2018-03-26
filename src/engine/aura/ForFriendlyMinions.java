package engine.aura;

import java.util.Set;

import engine.State;
import engine.event.EventHandler;

public interface ForFriendlyMinions<T extends EventHandler<?>> extends AuraSpec<T> {
	default Set<Integer> getTargets(int auraEmitter, State state) {
		Set<Integer> affects = ForOtherFriendlyMinions.getOtherFriendlyMinionss(auraEmitter, state);
		affects.add(auraEmitter);
		return affects;
	}
}