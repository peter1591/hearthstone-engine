package engine.utils;

public class EntityIds implements DeepCopyable<EntityIds> {
	int max_capacity;
	int[] ids;
	int size;

	private EntityIds() {
	}

	static public EntityIds create(int max_capacity) {
		EntityIds ret = new EntityIds();
		ret.max_capacity = max_capacity;
		ret.ids = new int[max_capacity];
		ret.size = 0;
		return ret;
	}

	public EntityIds deepCopy() {
		EntityIds ret = create(max_capacity);
		for (int i = 0; i < size; ++i) {
			ret.ids[i] = ids[i];
		}
		return ret;
	}

	public void insert(int idx, int entityId) {
		assert size < max_capacity;
		assert idx <= size;

		int i;
		for (i = size; i > idx; --i) {
			ids[i] = ids[i - 1];
		}
		assert i == idx;
		
		ids[idx] = entityId;
		size++;
	}
	
	public void pushBack(int entityId) {
		insert(size, entityId);
	}
	
	public void remove(int idx) {
		assert idx <= size;
		
		for (int i = idx; i < size - 1; ++i) {
			ids[i] = ids[i+1];
		}
		--size;
	}
}
