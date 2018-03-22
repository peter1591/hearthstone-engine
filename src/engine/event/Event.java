package engine.event;

// TODO: separate out game-entity events
public enum Event {
	CHECK_PLAYABLE,
	BEFORE_ZONE_CHANGE,
	REMOVED_FROM_ZONE,
	ADDED_TO_ZONE,
	PROPERTY_MODIFIERS, // property modifiers which needs to know the context (e.g., State)
	BEFORE_UPDATE_PROPERTY_MODIFIERS,
	ON_PLAY, // battlecry, spell effect, etc.
	AFTER_ATTACKED,
	ON_DAMAGED,
	BEFORE_TURN_END,
	AFTER_TURN_STARTED
}
