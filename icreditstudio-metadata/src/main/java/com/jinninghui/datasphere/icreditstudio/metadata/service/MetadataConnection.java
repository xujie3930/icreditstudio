package com.jinninghui.datasphere.icreditstudio.metadata.service;

import java.sql.Connection;

/**
 * @author Peng
 */
public interface MetadataConnection {
    /**
     * 获取连接
     *
     * @return
     */
    Connection getConnection();
}
