package engine.state.entity;

import engine.utils.CopyableAsBase;
import engine.utils.DeepCopyable;
import engine.utils.OverlayedArrayList;

public class EntityManager implements DeepCopyable<EntityManager>, CopyableAsBase<EntityManager> {
	public static final int UNKNOWN_ENTITY_ID = -1;
	
	OverlayedArrayList<Entity> entities;
	
	private EntityManager() {
	}
	
	static public EntityManager create() {
		EntityManager ret = new EntityManager();
		ret.entities = OverlayedArrayList.create();
		return ret;
	}
	
	@Override
	public EntityManager deepCopy() {
		EntityManager ret = new EntityManager();
		ret.entities = entities.deepCopy();
		return ret;
	}
	
	@Override
	public EntityManager copyAsBase() {
		EntityManager ret = new EntityManager();
		ret.entities = entities.copyAsBase();
		return ret;
	}
	
	public int add(Entity entity) {
		return entities.add(entity);
	}
	
	public Entity get(int index) {
		return entities.get(index);
	}
}
