package engine.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Create a two-layer container, to support copy-on-write if there's only one
 * copy.
 * 
 * @author petershih
 *
 * @param <T>
 *            Items in the array list.
 */
public class OverlayedArrayList<T extends DeepCopyable<T>>
		implements Iterable<T>, DeepCopyable<OverlayedArrayList<T>>, CopyableAsBase<OverlayedArrayList<T>> {
	private List<T> base; // immutable
	private ArrayList<T> overlay;

	private OverlayedArrayList() {
	}

	static public <T extends DeepCopyable<T>> OverlayedArrayList<T> create() {
		OverlayedArrayList<T> ret = new OverlayedArrayList<>();
		ret.overlay = new ArrayList<>();
		return ret;
	}

	public OverlayedArrayList<T> deepCopy() {
		OverlayedArrayList<T> ret = new OverlayedArrayList<>();
		ret.base = base; // just copy the reference, since it's immutable
		ret.overlay = new ArrayList<>();
		for (T item : overlay) {
			ret.overlay.add(item.deepCopy());
		}
		return ret;
	}

	public OverlayedArrayList<T> copyAsBase() {
		assert base == null;

		OverlayedArrayList<T> ret = new OverlayedArrayList<>();
		ret.base = Collections.unmodifiableList(overlay);
		for (int i = 0; i < ret.base.size(); ++i) {
			ret.overlay.add(null);
		}
		return ret;
	}

	public T get(int index) {
		T ret = overlay.get(index);
		if (ret != null)
			return ret;

		if (base == null)
			return null;
		ret = base.get(index);
		return ret;
	}

	public void set(int index, T item) {
		overlay.set(index, item);
	}

	public int add(T item) {
		int ret = overlay.size();
		overlay.add(item);
		return ret;
	}

	public int size() {
		return overlay.size();
	}
	
	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = new Iterator<T>() {
			
			private int idx = 0;

			@Override
			public boolean hasNext() {
				return idx < size();
			}

			@Override
			public T next() {
				return get(idx);
			}
		};
		return it;
	}
}
