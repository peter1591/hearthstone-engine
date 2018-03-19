package engine.entity;

import engine.event.EventManager;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;
import engine.utils.LayeredContainers;

public class Entity implements DeepCopyable<Entity>, CopyableAsBase<Entity> {
	Property initialProperty;
	
	LayeredContainers<ModifierLayer, Modifiers> modifiers;
	Property finalProperty;
	
	EventManager eventManager;
	
	private Entity() {
	}
	
	static public Entity create() {
		Entity ret = new Entity();
		ret.initialProperty = Property.create();
		ret.modifiers = LayeredContainers.create(ModifierLayer.class);
		ret.finalProperty = Property.create();
		ret.eventManager = EventManager.create();
		
		ret.recalculateProperty();
		return ret;
	}
	
	@Override
	public Entity deepCopy() {
		Entity ret = new Entity();
		ret.initialProperty = initialProperty.deepCopy();
		ret.modifiers = modifiers.deepCopy();
		ret.finalProperty = finalProperty.deepCopy();
		ret.eventManager = eventManager.deepCopy();
		return ret;
	}
	
	@Override
	public Entity copyAsBase() {
		Entity ret = new Entity();
		ret.initialProperty = initialProperty.copyAsBase();
		ret.modifiers = modifiers.copyAsBase();
		ret.finalProperty = finalProperty.copyAsBase();
		ret.eventManager = eventManager.copyAsBase();
		return ret;
	}
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public Property getInitialProperty() {
		return initialProperty;
	}
	
	public ReadableProperty getFinalProperty() {
		return finalProperty;
	}
	
	public Property getMutableProperty() {
		return finalProperty;
	}
	
	public void recalculateProperty() {
		finalProperty.setAs(initialProperty);
		for (ModifierLayer layer : ModifierLayer.values()) {
			modifiers.get(layer).apply(finalProperty);
		}
	}
	
	public int addPropertyModifier(ModifierLayer layer, Modifier propertyModifier) {
		return modifiers.get(layer).add(propertyModifier);
	}
	
	public void removePropertyModifier(ModifierLayer layer, int index) {
		modifiers.get(layer).remove(index);
	}
}
