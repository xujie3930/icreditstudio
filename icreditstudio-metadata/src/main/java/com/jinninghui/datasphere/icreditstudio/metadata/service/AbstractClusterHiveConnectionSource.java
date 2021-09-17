package com.jinninghui.datasphere.icreditstudio.metadata.service;

import java.util.Set;

/**
 * @author peng
 */
public abstract class AbstractClusterHiveConnectionSource implements ConnectionSource {
    @Override
    final public String getUrl() {
        throw new UnsupportedOperationException();
    }

    public abstract Set<String> getIpPorts();
}
