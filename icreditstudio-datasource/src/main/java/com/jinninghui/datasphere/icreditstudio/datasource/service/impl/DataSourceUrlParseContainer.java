package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import com.jinninghui.datasphere.icreditstudio.datasource.service.AbstractMapContainer;
import com.jinninghui.datasphere.icreditstudio.datasource.service.ConnectionSourceParser;

/**
 * @author Peng
 */
public class DataSourceUrlParseContainer extends AbstractMapContainer<String, ConnectionSourceParser> {

    private static DataSourceUrlParseContainer instance = new DataSourceUrlParseContainer();
    private DataSourceUrlParseContainer() {
    }
    public static DataSourceUrlParseContainer getInstance() {
        return instance;
    }
}
