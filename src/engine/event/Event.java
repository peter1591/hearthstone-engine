package engine.event;

public enum Event {
	SIDE_ZONE_CHANGED,
	PROPERTY_MODIFIERS, // property modifiers which is provided by an aura
	BEFORE_UPDATE_PROPERTY_MODIFIERS,
	BEFORE_PLAY_CARD,
	AFTER_PLAY_CARD,
	AFTER_ATTACKED,
	ON_DAMAGED
}
