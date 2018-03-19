package engine.entity;

import engine.board.Board.PlayerId;
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
	
	public void setSide(PlayerId side) {
		this.side = side;
	}
	
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	public void setSilenced(boolean silenced) {
		this.silenced = silenced;
	}
}
