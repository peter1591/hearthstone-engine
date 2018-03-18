package engine.utils;

public interface CopyAsBaseByDeepCopy<T> {
	T deepCopy();
	
	default T copyAsBase() {
		return deepCopy();
	}
}
