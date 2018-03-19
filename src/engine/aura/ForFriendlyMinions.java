package engine.aura;

import java.util.Set;

import engine.State;

public interface ForFriendlyMinions extends AuraUpdaterSpec {
	default Set<Integer> getTargets(int auraEmitter, State state) {
		Set<Integer> affects = ForOtherFriendlyMinions.getOtherFriendlyMinionss(auraEmitter, state);
		affects.add(auraEmitter);
		return affects;
	}
}