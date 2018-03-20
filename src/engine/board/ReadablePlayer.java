package engine.board;

import engine.utils.EntityIds;

public class ReadablePlayer {
	static final int MAX_MINIONS = 7;
	static final int MAX_HAND_CARDS = 10;

	int entityId;
	int hero;
	EntityIds minions;
	EntityIds hand;
	int hero_power;
	int weapon;
	
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
	
	public int getWeaponEntityId() {
		return weapon;
	}
}
