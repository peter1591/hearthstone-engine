package engine.aura;

import java.util.Set;

import engine.ManagedState;
import engine.event.EntityEventManager;
import engine.event.EventHandler;
import engine.utils.DeepCopyable;

/**
 * This class defines the behavior of an aura. Several mixins are provided to
 * make it easier to implement.
 * 
 * For example:
 * public class Aura implements 
 *		WhenMinionInPlayZone,
 *		ForOtherFriendlyMinions,
 *		ToModifyProperty
 * {
 * }
 *
 * @author petershih
 *
 */
public interface AuraSpec<T extends EventHandler<?>> extends DeepCopyable<AuraSpec<T>> {
	boolean exists(int auraEmitter, ManagedState state);
	
	default boolean enabled(int auraEmitter, ManagedState state) {
		return true;
	}

	Set<Integer> getTargets(int auraEmitter, ManagedState state);
	
	int addEvent(EntityEventManager eventManager, int targetEntity);
	void removeEvent(EntityEventManager eventManager, int targetEntity, int index);

	T createEffectHandler();

	@Override
	default AuraSpec<T> deepCopy() {
		return this; // this should be a stateless object, so it's safe to share instance
	}
}