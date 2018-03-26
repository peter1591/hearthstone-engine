package engine.event;

// property modifiers which needs to know the context (e.g., State)
public class EventCheckPlayable {
	public static class Argument extends EntityEventArgument {
		public boolean playable = true;
	}
}
