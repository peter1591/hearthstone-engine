package engine.event;

import engine.board.Board.PlayerId;
import engine.entity.Entity;
import engine.entity.ReadableProperty.Zone;

public class EventArgument {
	/**
	 * The entity who owns this event handler.
	 */
	public Entity owner;
	
	/**
	 * Who triggers this event.
	 */
	public int triggerer;
	
	public int amount;

	// for zone change event
	public PlayerId side;
	public Zone zone;
	public int zoneIndex;
	
	/**
	 * should we abort the operation
	 */
	public boolean abort = false;
	
	public boolean booleanResult;
}
