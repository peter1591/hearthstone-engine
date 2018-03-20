package engine.aura;

import java.util.Set;

import engine.State;
import engine.event.Event;
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
public interface AuraSpec extends DeepCopyable<AuraSpec> {
	boolean exists(int auraEmitter, State state);
	
	default boolean enabled(int auraEmitter, State state) {
		return true;
	}

	Set<Integer> getTargets(int auraEmitter, State state);

	Event getEffectEvent();

	EventHandler createEffectHandler();

	@Override
	default AuraSpec deepCopy() {
		return this; // this should be a stateless object, so it's safe to share instance
	}
}