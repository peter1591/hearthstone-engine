package engine.event;

import engine.utils.Registry;

public class HandlerRegister {
	Registry<EventHandler> registry;
	
	private HandlerRegister() {
	}
	
	static HandlerRegister instance = null;
	static HandlerRegister getInstance() {
		if (instance == null) {
			instance = new HandlerRegister();
		}
		return instance;
	}
	
	public void register(String key, EventHandler item) {
		registry.register(key, item);
	}
	
	public EventHandler get(String key) {
		return registry.get(key);
	}
}
