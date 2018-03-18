package engine;

public interface GameAction {
	enum MainOp {
		PLAY_CARD,
		ATTACK,
		HERO_POWER,
		END_TURN
	}
	
	MainOp getMainOp();
}
