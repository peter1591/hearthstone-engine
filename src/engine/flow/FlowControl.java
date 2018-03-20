package engine.flow;

import engine.ManagedState;

public class FlowControl {
	public static void Initialize(ManagedState state) {
		WeaponFlowHandler.Process(state);
	}
}
