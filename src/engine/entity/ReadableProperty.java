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
	
	PlayerId side = PlayerId.UNKNOWN;
	Zone zone = Zone.UNKNOWN;
	
	int cost;
	int hp;
	int maxHp;
	int attack;
	
	boolean silenced = false;
	
	protected ReadableProperty() {
	}
	
	public PlayerId getSide() {
		return side;
	}
	
	public Zone getZone() {
		return zone;
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
}
