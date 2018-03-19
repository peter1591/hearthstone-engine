package engine.aura;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import engine.State;
import engine.event.Event;
import engine.event.EventArgument;
import engine.event.EventHandler;

/**
 * An aura works as follows.
 * 
 * 1. In ZoneChanged event, register the aura updater to AuraUpdate event.
 * 
 * 2. In AuraUpdate event, apply aura effects to each affected entities, and
 * remove to unaffected ones.
 * 
 * This class is the helper to implement such an aura updater. Simply implement
 * the Spec class, and you're all set.
 * 
 * Limited that only one effect can be applied on each entity. If you really
 * need to apply multiple effects on one single entity, one way is to use
 * several aura updaters.
 * 
 * @author petershih
 *
 */
public final class AuraUpdaterHandler implements EventHandler {
	boolean owned = false;
	int auraEmitter;
	HashMap<Integer, Integer> appliedEffects;
	AuraUpdaterSpec spec;

	private AuraUpdaterHandler() {

	}

	private void addAuraEffect(State state, int targetEntity) {
		if (appliedEffects.containsKey(targetEntity))
			return;

		EventHandler effect = spec.createEffectHandler();
		effect.setOwned(true);
		int idx = state.getEntityManager().get(targetEntity).getEventManager().add(spec.getEffectEvent(), effect);

		appliedEffects.put(targetEntity, idx);
	}

	private void removeAuraEffect(State state, int targetEntity) {
		if (!appliedEffects.containsKey(targetEntity))
			return;

		int index = appliedEffects.get(targetEntity);
		state.getEntityManager().get(targetEntity).getEventManager().markRemoved(spec.getEffectEvent(), index, true);

		appliedEffects.remove(targetEntity);
	}

	private void removeAllAuraEffects(State state) {
		for (int existTarget : appliedEffects.keySet()) {
			removeAuraEffect(state, existTarget);
		}
	}

	static public AuraUpdaterHandler create(int auraEmitter, AuraUpdaterSpec spec) {
		AuraUpdaterHandler ret = new AuraUpdaterHandler();
		ret.auraEmitter = auraEmitter;
		ret.appliedEffects = new HashMap<>();
		ret.spec = spec;
		return ret;
	}

	@Override
	public boolean invoke(Event event, State state, EventArgument argument) {
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
	public AuraUpdaterHandler deepCopy() {
		AuraUpdaterHandler ret = new AuraUpdaterHandler();
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