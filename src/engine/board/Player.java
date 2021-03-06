package engine.board;

import engine.board.Board.PlayerId;
import engine.entity.Entity;
import engine.entity.EntityManager;
import engine.utils.DeepCopyable;
import engine.utils.EntityIds;

public class Player extends ReadablePlayer implements DeepCopyable<Player> {	
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
	
	public void callbackAfterRemoved(PlayerId side, Entity entity) {
		// TODO
	}
	
	public void callbackAfterAdded(PlayerId side, Entity entity) {
		// TODO
	}
	
	public void setWeapon(int entityId) {
		assert entityId != EntityManager.UNKNOWN_ENTITY_ID;
		weapon = entityId;
	}
	public void unsetWeapon() {
		weapon = EntityManager.UNKNOWN_ENTITY_ID;
	}
	
	public void addMinion(int entityId, int index) {
		minions.insert(index, entityId);
	}
}
