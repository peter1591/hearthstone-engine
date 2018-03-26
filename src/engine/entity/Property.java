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
	
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
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

	public void setResourceMax(int resourceMax) {
		this.resourceMax = resourceMax;
	}

	public void setResourceCurrent(int resourceCurrent) {
		this.resourceCurrent = resourceCurrent;
	}

	public void setResourceLocked(int resourceLocked) {
		this.resourceLocked = resourceLocked;
	}

	public void setResourceLockedNext(int resourceLockedNext) {
		this.resourceLockedNext = resourceLockedNext;
	}

	public void setPlayable(boolean playable) {
		this.playable = playable;
	}
}
