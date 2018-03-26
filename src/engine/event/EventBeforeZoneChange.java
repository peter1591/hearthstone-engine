package engine.event;

import engine.board.Board.PlayerId;
import engine.entity.ReadableProperty.Zone;

public class EventBeforeZoneChange {
	public static class Argument extends EventArgument {
		public PlayerId side;
		public Zone zone;
		public boolean abort = false;
		
		public Argument(PlayerId side, Zone zone) {
			this.side = side;
			this.zone = zone;
		}
	}
}
