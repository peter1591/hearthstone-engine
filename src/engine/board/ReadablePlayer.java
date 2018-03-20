package engine.board;

import engine.entity.EntityManager;
import engine.utils.EntityIds;

public class ReadablePlayer {
	static final int MAX_MINIONS = 7;
	static final int MAX_HAND_CARDS = 10;

	int entityId = EntityManager.UNKNOWN_ENTITY_ID;
	int hero = EntityManager.UNKNOWN_ENTITY_ID;
	EntityIds minions;
	EntityIds hand;
	int hero_power = EntityManager.UNKNOWN_ENTITY_ID;
	int weapon = EntityManager.UNKNOWN_ENTITY_ID;
	
	public int getPlayerEntityId() {
		return entityId;
	}
	
	public int getHeroEntityId() {
		return hero;
	}
	
	public int getMinionsCount() {
		return minions.size();
	}
	
	public int getMinionEntityId(int index) {
		return minions.get(index);
	}
	
	public int getHandCount() {
		return hand.size();
	}
	
	public int getHandEntityId(int index) {
		return hand.get(index);
	}
	
	public int getHeroPowerEntityId() {
		return hero_power;
	}
	
	public boolean hasWeapon() {
		return weapon != EntityManager.UNKNOWN_ENTITY_ID;
	}
	public int getWeaponEntityId() {
		return weapon;
	}
}
