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

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	public void addAttack(int val) {
		this.attack += val;
	}
}
