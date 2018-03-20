package engine;

import engine.flow.FlowControl;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;

/**
 * The game engine controls the whole game flow. This object allows only the available actions
 * which can be performed by the current player.
 * 
 * This class is deep copyable.
 * 
 * This class is capable to copy as base. That is, create a new copy which acts as it is deep-copied, but
 * actually referring to the original object as the base. This is a special case for copy-on-write, and is
 * useful for a DFS/BFS to iterate over all possible actions.
 * 
 * @author petershih
 *
 */
public class Game implements DeepCopyable<Game>, CopyableAsBase<Game> {
	public enum Result {
		FIRST_PLAYER_WIN, SECOND_PLAYER_WIN, DRAW
	}
	
	State state;

	private Game() {

	}

	public static Game createWithInitializedState(State state) {
		Game ret = new Game();
		ret.state = state;
		FlowControl.Initialize(new ManagedState(state));
		return ret;
	}

	@Override
	public Game deepCopy() {
		Game ret = new Game();
		ret.state = state.deepCopy();
		return ret;
	}

	@Override
	public Game copyAsBase() {
		Game ret = new Game();
		ret.state = state.copyAsBase();
		return ret;
	}

	public Result PerformAction(GameAction action) {
		GameAction.MainOp op = action.getMainOp();
		if (op == GameAction.MainOp.PLAY_CARD)
			return PlayCard(action);
		if (op == GameAction.MainOp.ATTACK)
			return Attack(action);
		if (op == GameAction.MainOp.HERO_POWER) 
			return HeroPower(action);
		if (op == GameAction.MainOp.END_TURN)
			return EndTurn(action);
		throw new RuntimeException("Invalid main op.");
	}

	private Result PlayCard(GameAction action) {

	}
	
	private Result Attack(GameAction action) {
		
	}
	
	private Result HeroPower(GameAction action) {
		
	}
	
	private Result EndTurn(GameAction action) {
		
	}
}
