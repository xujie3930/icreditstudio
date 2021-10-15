package com.jinninghui.datasphere.icreditstudio.datasource.service;

/**
 * @author Peng
 */
public interface MapContainer<K, T> extends Container<K, T> {
    /**
     * 添加数据
     *
     * @param key
     * @param t
     */
    void put(K key, T t);
}
