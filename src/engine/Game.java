package engine;

import engine.board.Board.PlayerId;
import engine.entity.ReadableProperty;
import engine.entity.ReadableProperty.CardType;
import engine.entity.ReadableProperty.Zone;
import engine.flow.FlowControl;
import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;

/**
 * The game engine controls the whole game flow. This object allows only the
 * available actions which can be performed by the current player.
 * 
 * This class is deep copyable.
 * 
 * This class is capable to copy as base. That is, create a new copy which acts
 * as it is deep-copied, but actually referring to the original object as the
 * base. This is a special case for copy-on-write, and is useful for a DFS/BFS
 * to iterate over all possible actions.
 * 
 * @author petershih
 *
 */
public class Game implements DeepCopyable<Game>, CopyableAsBase<Game> {
	public enum Result {
		PLAYING, FIRST_PLAYER_WIN, SECOND_PLAYER_WIN, DRAW
	}

	ManagedState state;

	private Game() {

	}

	public static Game create(State state) {
		Game ret = new Game();
		ret.state = ManagedState.create(state);
		FlowControl.Initialize(ret.state);
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
		state.getFlowContext().gameAction = action;

		GameAction.MainOp op = state.getFlowContext().gameAction.getMainOp(state);
		if (op == GameAction.MainOp.PLAY_CARD)
			PlayCard();
		else if (op == GameAction.MainOp.ATTACK)
			Attack();
		else if (op == GameAction.MainOp.HERO_POWER)
			HeroPower();
		else if (op == GameAction.MainOp.END_TURN)
			EndTurn();
		else
			throw new RuntimeException("Invalid main op.");
		return getResult();
	}

	private void PlayCard() {
		int handCardIndex = state.getFlowContext().gameAction.getHandCardIndex(state);
		int entityId = state.getCurrentPlayer().getHandEntityId(handCardIndex);

		ReadableProperty entityProperty = state.getEntityProperty(entityId);
		int cost = entityProperty.getCost();
		state.spendCrystal(state.getCurrentPlayerId(), cost);

		if (entityProperty.getCardType() == CardType.MINION) {
			int minionPutIndex = state.getFlowContext().gameAction.getMinionPutIndex(state);
			state.addMinionToBoard(state.getCurrentPlayerId(), entityId, minionPutIndex);
		}

		state.invokeOnPlayEvent(entityId);
		
		if (entityProperty.getCardType() == CardType.SPELL) {
			state.changeZone(entityId, Zone.GRAVEYARD);
		}
	}

	private void Attack() {
		state.getFlowContext().attacker = state.getFlowContext().gameAction.getAttackerIndex(state).getEntityId(state);

		state.prepareAttackTargets();
		state.getFlowContext().defender = state.getFlowContext().gameAction.getDefenderIndex(state).getEntityId(state);

		int attack = state.getEntityProperty(state.getFlowContext().attacker).getAttack();
		state.damage(state.getFlowContext().attacker, state.getFlowContext().defender, attack);

		int counterAttack = state.getEntityProperty(state.getFlowContext().defender).getAttack();
		state.damage(state.getFlowContext().defender, state.getFlowContext().attacker, counterAttack);
	}

	private void HeroPower() {
		int entityId = state.getCurrentPlayer().getHeroPowerEntityId();
		state.invokeOnPlayEvent(entityId);
		state.markHeroPowerAsUsed(entityId);
	}

	private void EndTurn() {
		state.EndTurn();
	}

	private boolean isAlive(int entityId) {
		return state.getEntityProperty(entityId).getHp() > 0;
	}

	private Result getResult() {
		state.finalCleanUp();

		boolean firstAlive = isAlive(state.getPlayer(PlayerId.FIRST).getHeroEntityId());
		boolean secondAlive = isAlive(state.getPlayer(PlayerId.FIRST).getHeroEntityId());
		if (firstAlive) {
			if (secondAlive) {
				return Result.PLAYING;
			} else {
				return Result.FIRST_PLAYER_WIN;
			}
		} else {
			if (secondAlive) {
				return Result.SECOND_PLAYER_WIN;
			} else {
				return Result.DRAW;
			}
		}
	}
}
