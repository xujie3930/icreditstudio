package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import com.jinninghui.datasphere.icreditstudio.metadata.service.AbstractClusterHiveConnectionSource;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataConnection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;

/**
 * @author Peng
 */
@Component
public class HiveConnection implements MetadataConnection {
    @Resource
    private AbstractClusterHiveConnectionSource connectionSource;

    @Override
    public Connection getConnection() {
        return null;
    }
}
