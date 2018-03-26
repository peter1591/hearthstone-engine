package engine.event;

import engine.board.Board.PlayerId;
import engine.entity.ReadableProperty.Zone;

public class EventRemovedFromZone {
	public static class Argument extends EntityEventArgument {
		public PlayerId side;
		public Zone zone;
		
		public Argument(PlayerId side, Zone zone) {
			this.side = side;
			this.zone = zone;
		}
	}
}
