package engine.entity;

import java.util.Iterator;

import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;
import engine.utils.OverlayedArrayList;

public class Modifiers implements Iterable<Modifier>, DeepCopyable<Modifiers>, CopyableAsBase<Modifiers> {
	OverlayedArrayList<Modifier> items;
	
	static final Modifier removedModifier = Modifier.create(null);
	
	private Modifiers() {
	}
	
	public static Modifiers create() {
		Modifiers ret = new Modifiers();
		ret.items = OverlayedArrayList.create();
		return ret;
	}
	
	@Override
	public Modifiers deepCopy() {
		Modifiers ret = new Modifiers();
		ret.items = items.deepCopy();
		return ret;
	}
	
	@Override
	public Modifiers copyAsBase() {
		Modifiers ret = new Modifiers();
		ret.items = items.copyAsBase();
		return ret;
	}
	
	@Override
	public Iterator<Modifier> iterator() {
		return items.iterator();
	}
	
	public int add(Modifier item) {
		return items.add(item);
	}
	
	public void remove(int index) {
		items.set(index, removedModifier);
	}
	
	public void apply(Property property) {
		for (Modifier modifier: this) {
			if (modifier == removedModifier) continue;
			modifier.apply(property);
		}
	}
}
