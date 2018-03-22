package engine.entity;

import engine.board.Board.PlayerId;

public abstract class ReadableProperty {
	public static enum Zone {
		UNKNOWN,
		DECK,
		HAND,
		PLAY,
		GRAVEYARD,
		SETASIDE
	}
	
	public static enum CardType {
		UNKNOWN,
		MINION,
		SPELL,
		SECRET,
		QUEST,
		HERO,
		HERO_POWER
	}
	
	PlayerId side = PlayerId.UNKNOWN;
	Zone zone = Zone.UNKNOWN;
	CardType cardType = CardType.UNKNOWN;
	
	int cost;
	int hp;
	int maxHp;
	int attack;
	
	boolean silenced = false;
	
	int resourceMax;
	int resourceCurrent;
	int resourceLocked;
	int resourceLockedNext;
	
	/**
	 * If this entity is considered as playable.
	 * 
	 * For hand cards, a playable card means all the pre-requisite are met, including
	 * cost, has valid target, etc.
	 * 
	 * For minions, a playable entity means it's attackable.
	 * 
	 * For hero power, a playbale entity means it's usable.
	 */
	boolean playable;

	protected ReadableProperty() {
	}
	
	public PlayerId getSide() {
		return side;
	}
	
	public Zone getZone() {
		return zone;
	}
	
	public CardType getCardType() {
		return cardType;
	}

	public boolean getSilenced() {
		return silenced;
	}

	public int getCost() {
		return cost;
	}

	public int getHp() {
		return hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getAttack() {
		return attack;
	}	
	
	public int getResourceMax() {
		return resourceMax;
	}

	public int getResourceCurrent() {
		return resourceCurrent;
	}

	public int getResourceLocked() {
		return resourceLocked;
	}

	public int getResourceLockedNext() {
		return resourceLockedNext;
	}

	public boolean isPlayable() {
		return playable;
	}
}
