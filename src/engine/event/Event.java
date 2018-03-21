package engine.event;

public enum Event {
	BEFORE_ZONE_CHANGE,
	REMOVED_FROM_ZONE,
	ADDED_TO_ZONE,
	PROPERTY_MODIFIERS, // property modifiers which needs to know the context (e.g., State)
	BEFORE_UPDATE_PROPERTY_MODIFIERS,
	BEFORE_PLAY_CARD,
	AFTER_PLAY_CARD,
	AFTER_ATTACKED,
	ON_DAMAGED
}
