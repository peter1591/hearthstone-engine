package engine.entity;

import engine.utils.DeepCopyable;

public final class Modifier implements DeepCopyable<Modifier> {
	public interface Operation {
		void op(Property property);
	}
	
	Operation operation;
	
	private Modifier() {
	}
	
	public static Modifier create(Operation operation) {
		Modifier ret = new Modifier();
		ret.operation = operation;
		return ret;
	}
	
	public void apply(Property property) {
		operation.op(property);
	}
	
	@Override
	public Modifier deepCopy() {
		return this; // this is a stateless object
	}
}
