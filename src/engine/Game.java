package engine;

import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;

/**
 * The game engine controls the whole game flow.
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
