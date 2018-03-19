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
}
