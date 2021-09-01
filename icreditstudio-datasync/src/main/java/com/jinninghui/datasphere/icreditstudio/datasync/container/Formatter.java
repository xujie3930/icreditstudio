package com.jinninghui.datasphere.icreditstudio.datasync.container;

/**
 * @author Peng
 */
public interface Formatter<S> {
    /**
     * 格式化
     *
     * @param s
     * @return
     */
    String format(S s);
}
