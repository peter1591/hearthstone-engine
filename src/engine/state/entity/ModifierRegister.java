package engine.state.entity;

import engine.utils.Registry;

public class ModifierRegister {
	Registry<Modifier> registry;
	
	private ModifierRegister() {
	}
	
	static ModifierRegister instance = null;
	static ModifierRegister getInstance() {
		if (instance == null) {
			instance = new ModifierRegister();
		}
		return instance;
	}
	
	public void register(String key, Modifier item) {
		registry.register(key, item);
	}
	
	public Modifier get(String key) {
		return registry.get(key);
	}
}
