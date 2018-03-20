package engine.flow;

import engine.State;

public class FlowControl {
	public static void Initialize(State state) {
		WeaponFlowHandler.Process(state);
	}
}
