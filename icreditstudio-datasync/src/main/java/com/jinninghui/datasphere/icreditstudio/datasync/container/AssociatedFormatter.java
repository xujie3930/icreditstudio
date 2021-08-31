package com.jinninghui.datasphere.icreditstudio.datasync.container;

/**
 * @author peng
 */
public interface AssociatedFormatter<S> extends Formatter<S> {
    String completion(S s);
}
