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

    /**
     * hive集群节点IP:PORT集合
     *
     * @return
     */
    public abstract Set<String> getIpPorts();

    /**
     * hdfs访问地址
     *
     * @return
     */
    public abstract String getDefaultFS();

    /**
     * 数据存放地址
     *
     * @return
     */
    public abstract String getWareHouse();
}
