package engine.entity;

import engine.utils.CopyAsBaseByDeepCopy;
import engine.utils.DeepCopyable;

public class Property extends ReadableProperty implements DeepCopyable<Property>, CopyAsBaseByDeepCopy<Property> {
	private Property() {
		super();
	}

	public void setAs(ReadableProperty rhs) {
	}

	static public Property create() {
		Property ret = new Property();
		return ret;
	}

	@Override
	public Property deepCopy() {
		Property ret = new Property();
		return ret;
	}
}
