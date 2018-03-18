package engine.utils;

import java.util.EnumMap;
import java.util.Map.Entry;

public class LayeredContainers<Layer extends Enum<Layer>, ContainerType extends DeepCopyable<ContainerType> & CopyableAsBase<ContainerType>>
		implements DeepCopyable<LayeredContainers<Layer, ContainerType>>, CopyableAsBase<LayeredContainers<Layer, ContainerType>> {
	Class<Layer> clazz;
	EnumMap<Layer, ContainerType> layers;

	private LayeredContainers() {
	}

	static public <Layer extends Enum<Layer>, ContainerType extends DeepCopyable<ContainerType> & CopyableAsBase<ContainerType>> LayeredContainers<Layer, ContainerType> create(
			Class<Layer> clazz) {
		LayeredContainers<Layer, ContainerType> ret = new LayeredContainers<>();
		ret.clazz = clazz;
		ret.layers = new EnumMap<>(clazz);
		return ret;
	}

	public LayeredContainers<Layer, ContainerType> deepCopy() {
		LayeredContainers<Layer, ContainerType> ret = new LayeredContainers<>();
		ret.clazz = clazz;
		ret.layers = new EnumMap<>(clazz);
		for (Entry<Layer, ContainerType> item : layers.entrySet()) {
			ret.layers.put(item.getKey(), item.getValue().deepCopy());
		}
		return ret;
	}
	
	public LayeredContainers<Layer, ContainerType> copyAsBase() {
		LayeredContainers<Layer, ContainerType> ret = new LayeredContainers<>();
		ret.clazz = clazz;
		ret.layers = new EnumMap<>(clazz);
		for (Entry<Layer, ContainerType> item : layers.entrySet()) {
			ret.layers.put(item.getKey(), item.getValue().copyAsBase());
		}
		return ret;
	}
	
	public ContainerType get(Layer layer) {
		return layers.get(layer);
	}
}
