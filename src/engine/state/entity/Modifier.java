package engine.state.entity;

import engine.utils.DeepCopyable;

public final class Modifier implements DeepCopyable<Modifier> {
	public interface Operation {
		void op(Property property);
	}
	
	Operation operation;
	
	private Modifier() {
	}
	
	public static Modifier createAndRegister(String name, Operation operation) {
		Modifier ret = new Modifier();
		ret.operation = operation;
		
		ModifierRegister.getInstance().register(name, ret);
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
