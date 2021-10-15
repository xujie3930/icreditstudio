package com.jinninghui.datasphere.icreditstudio.datasource.service;

/**
 * @author Peng
 */
public interface Parser<S, T> {
    /**
     * 解析
     *
     * @param s
     * @return
     */
    T parse(S s);
}
