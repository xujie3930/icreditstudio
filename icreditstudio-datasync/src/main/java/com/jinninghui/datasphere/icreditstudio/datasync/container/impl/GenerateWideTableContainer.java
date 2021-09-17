package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.GenerateWideTable;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncGenerateWideTableParam;

import java.util.Map;

/**
 * @author Peng
 */
public class GenerateWideTableContainer extends AbstractMapContainer<String, GenerateWideTable> {

    private static GenerateWideTableContainer instance = new GenerateWideTableContainer();

    private GenerateWideTableContainer() {
    }

    public static GenerateWideTableContainer getInstance() {
        return instance;
    }

    public static GenerateWideTable find(DataSyncGenerateWideTableParam param) {
        GenerateWideTable result = null;
        Map<String, GenerateWideTable> container = GenerateWideTableContainer.getInstance().getContainer();
        for (Map.Entry<String, GenerateWideTable> entry : container.entrySet()) {
            if (entry.getValue().isCurrentWideTable(param)) {
                result = entry.getValue();
            }
        }
        return result;
    }
}
