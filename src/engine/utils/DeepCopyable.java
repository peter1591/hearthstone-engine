package engine.utils;

/**
 * This object is going to be cloned silently. Do not store the reference
 * anywhere once you passed it to a container.
 * 
 * @author petershih
 *
 * @param <T>
 */
public interface DeepCopyable<T> {
	T deepCopy();
}
