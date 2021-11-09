package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import cn.hutool.core.util.StrUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.DataSyncQuery;

/**
 * @author Peng
 */
public class DataSyncQueryContainer extends AbstractMapContainer<String, DataSyncQuery> {

    private static DataSyncQueryContainer instance = new DataSyncQueryContainer();

    private DataSyncQueryContainer() {
    }

    public static DataSyncQueryContainer getInstance() {
        return instance;
    }

    public static DataSyncQuery matching(String sql) {
        if (StrUtil.contains(sql, "select") && StrUtil.contains(sql, "from")) {
            return getInstance().find("mysql");
        } else {
            return getInstance().find("mysql");
        }
    }
}
