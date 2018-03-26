package engine.event;

public class EventOnDamaged {
	public static class Argument extends EntityEventArgument {
		public int amount;
		
		public Argument(int amount) {
			this.amount = amount;
		}
	}
}
