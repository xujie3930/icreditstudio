package com.jinninghui.datasphere.icreditstudio.datasource.service;

/**
 * 容器
 *
 * @author Peng
 */
public interface Container<K, T> {
    /**
     * 查找数据
     *
     * @param k
     * @return
     */
    T find(K k);
}