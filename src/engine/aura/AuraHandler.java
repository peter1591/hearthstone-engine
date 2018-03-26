package engine.aura;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import engine.ManagedState;
import engine.event.EventArgument;
import engine.event.EventHandler;

/**
 * An aura works as follows.
 * 
 * 1. In ZoneChanged event, register the aura updater to the event this aura
 * is listening to.
 * 
 * 2. In the listening event, apply aura effects to each affected entities, and
 * remove to unaffected ones.
 * 
 * This class is the helper to implement such an aura event listen. Simply implement
 * the AuraSpec class, and you're all set.
 * 
 * Limited that only one effect can be applied on each entity. If you really
 * need to apply multiple effects on one single entity, one way is to use
 * several aura updaters.
 * 
 * @author petershih
 *
 */
public final class AuraHandler<T extends EventArgument> implements EventHandler<T> {
	boolean owned = false;
	int auraEmitter;
	HashMap<Integer, Integer> appliedEffects;
	AuraSpec<EventHandler<T>> spec;

	private AuraHandler() {

	}

	private void addAuraEffect(ManagedState state, int targetEntity) {
		if (appliedEffects.containsKey(targetEntity))
			return;

		int idx = spec.addEvent(state.getEntityEventManager(targetEntity), targetEntity);

		appliedEffects.put(targetEntity, idx);
	}

	private void removeAuraEffect(ManagedState state, int targetEntity) {
		if (!appliedEffects.containsKey(targetEntity))
			return;
		
		int index = appliedEffects.get(targetEntity);
		spec.removeEvent(state.getEntityEventManager(targetEntity), targetEntity, index);

		appliedEffects.remove(targetEntity);
	}

	private void removeAllAuraEffects(ManagedState state) {
		for (int existTarget : appliedEffects.keySet()) {
			removeAuraEffect(state, existTarget);
		}
	}

	static public <T extends EventArgument> AuraHandler<T> create(int auraEmitter, AuraSpec<EventHandler<T>> spec) {
		AuraHandler<T> ret = new AuraHandler<>();
		ret.auraEmitter = auraEmitter;
		ret.appliedEffects = new HashMap<>();
		ret.spec = spec;
		return ret;
	}

	@Override
	public boolean invoke(ManagedState state, T argument) {
		if (!spec.exists(auraEmitter, state)) {
			removeAllAuraEffects(state);
			return false;
		}

		if (!spec.enabled(auraEmitter, state)) {
			removeAllAuraEffects(state);
			return true;
		}

		Set<Integer> newTargets = spec.getTargets(auraEmitter, state);

		for (int newTarget : newTargets) {
			if (appliedEffects.containsKey(newTarget))
				continue;
			addAuraEffect(state, newTarget);
		}
		for (int existTarget : appliedEffects.keySet()) {
			if (newTargets.contains(existTarget))
				continue;
			removeAuraEffect(state, existTarget);
		}

		return true;
	}

	@Override
	public AuraHandler<T> deepCopy() {
		AuraHandler<T> ret = new AuraHandler<>();
		ret.auraEmitter = auraEmitter;
		ret.appliedEffects = new HashMap<>();
		for (Entry<Integer, Integer> item : appliedEffects.entrySet()) {
			ret.appliedEffects.put(item.getKey(), item.getValue());
		}
		ret.spec = spec.deepCopy();
		return ret;
	}

	@Override
	public boolean isOwned() {
		return owned;
	}

	@Override
	public void setOwned(boolean owned) {
		this.owned = owned;
	}
}