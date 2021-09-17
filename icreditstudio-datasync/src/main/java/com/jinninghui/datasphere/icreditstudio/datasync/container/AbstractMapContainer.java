package com.jinninghui.datasphere.icreditstudio.datasync.container;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peng
 */
@Data
public abstract class AbstractMapContainer<K, T> implements MapContainer<K, T> {
    private Map<K, T> container = new ConcurrentHashMap<>();

    @Override
    public T find(K k) {
        return container.get(k);
    }

    @Override
    public void put(K key, T t) {
        container.putIfAbsent(key, t);
    }
}
