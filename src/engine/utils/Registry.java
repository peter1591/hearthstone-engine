package engine.utils;

import java.util.HashMap;

public class Registry<T> {
	HashMap<String, T> items;
	
	public void register(String key, T value) {
		items.put(key, value);
	}
	
	public T get(String key) {
		return items.get(key);
	}
}
