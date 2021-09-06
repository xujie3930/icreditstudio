package com.jinninghui.datasphere.icreditstudio.metadata.service;

import com.jinninghui.datasphere.icreditstudio.metadata.common.Database;
import com.jinninghui.datasphere.icreditstudio.metadata.common.WarehouseDataSource;

import java.util.List;

/**
 * @author Peng
 */
public interface MetadataService {
    /**
     * 获取数据库列表
     *
     * @return
     */
    List<Database> getDatabases();

    /**
     * 获取仓库源列表
     *
     * @return
     */
    List<WarehouseDataSource> getWarehouseDataSources();
}
