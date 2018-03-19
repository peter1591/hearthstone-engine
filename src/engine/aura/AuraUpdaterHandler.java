package engine.aura;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import engine.State;
import engine.event.Event;
import engine.event.EventArgument;
import engine.event.EventHandler;

/**
 * An aura works as follows. 1. In ZoneChanged event, register the aura updater
 * to AuraUpdate event. 2. In AuraUpdate event, apply aura effects to each
 * affected entities, and remove to unaffected ones.
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
	int auraEmitter;
	HashMap<Integer, Integer> appliedEffects;
	AuraUpdaterSpec spec;

	private AuraUpdaterHandler() {

	}

	private void addAuraEffect(State state, int targetEntity) {
		if (appliedEffects.containsKey(targetEntity))
			return;

		int idx = state.getEntityManager().get(targetEntity).getEventManager().add(spec.getEffectEvent(),
				spec.createEffectHandler());

		appliedEffects.put(targetEntity, idx);
	}

	private void removeAuraEffect(State state, int targetEntity) {
		if (!appliedEffects.containsKey(targetEntity))
			return;

		int index = appliedEffects.get(targetEntity);
		state.getEntityManager().get(targetEntity).getEventManager().markRemoved(spec.getEffectEvent(), index);

		appliedEffects.remove(targetEntity);
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
		if (!spec.isAuraValid(auraEmitter, state)) {
			for (int existTarget : appliedEffects.keySet()) {
				removeAuraEffect(state, existTarget);
			}
			return false;
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
}