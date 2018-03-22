package engine;

import engine.board.ReadablePlayer;
import engine.board.Board.PlayerId;

public interface GameAction {
	enum MainOp {
		PLAY_CARD,
		ATTACK,
		HERO_POWER,
		END_TURN
	}
	
	static class BoardEntityIndex {
		PlayerId side;
		boolean isHero;
		int index;
		
		int getEntityId(ManagedState state) {
			ReadablePlayer player = state.getPlayer(side);
			if (isHero)
				return player.getHeroEntityId();
			else
				return player.getMinionEntityId(index);
		}
	}
	
	MainOp getMainOp(ManagedState state);
	int getHandCardIndex(ManagedState state);
	int getMinionPutIndex(ManagedState state); // 0 for leftmost, size(minions) for rightmost
	BoardEntityIndex getAttackerIndex(ManagedState state);
	BoardEntityIndex getDefenderIndex(ManagedState state);
}
