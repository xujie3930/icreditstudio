package com.jinninghui.datasphere.icreditstudio.datasync.container.widetable.outside;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.OutsideSourceWideTableParam;

import java.util.Map;

/**
 * @author Peng
 */
public class OutsideGenerateWideTableContainer extends AbstractMapContainer<String, OutsideGenerateWideTable> {

    private static OutsideGenerateWideTableContainer instance = new OutsideGenerateWideTableContainer();

    private OutsideGenerateWideTableContainer() {
    }

    public static OutsideGenerateWideTableContainer getInstance() {
        return instance;
    }

    public static OutsideGenerateWideTable find(OutsideSourceWideTableParam param) {
        OutsideGenerateWideTable result = null;
        Map<String, OutsideGenerateWideTable> container = OutsideGenerateWideTableContainer.getInstance().getContainer();
        for (Map.Entry<String, OutsideGenerateWideTable> entry : container.entrySet()) {
            if (entry.getValue().isCurrentTypeHandler(param)) {
                result = entry.getValue();
            }
        }
        return result;
    }
}
