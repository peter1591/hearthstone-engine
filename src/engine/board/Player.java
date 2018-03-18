package engine.board;

import engine.entity.EntityManager;
import engine.utils.DeepCopyable;
import engine.utils.EntityIds;

public class Player implements DeepCopyable<Player> {
	static final int MAX_MINIONS = 7;
	static final int MAX_HAND_CARDS = 10;

	int entityId;
	int hero;
	EntityIds minions;
	EntityIds hand;
	int hero_power;
	
	private Player() {
	}
	
	static public Player create() {
		Player ret = new Player();
		ret.hero = EntityManager.UNKNOWN_ENTITY_ID;
		ret.minions = EntityIds.create(MAX_MINIONS);
		ret.hand = EntityIds.create(MAX_HAND_CARDS);
		ret.hero_power = EntityManager.UNKNOWN_ENTITY_ID;
		return ret;
	}
	
	public Player deepCopy() {
		Player ret = new Player();
		ret.hero = hero;
		ret.minions = minions.deepCopy();
		ret.hand = hand.deepCopy();
		ret.hero_power = hero_power;
		return ret;
	}
}
