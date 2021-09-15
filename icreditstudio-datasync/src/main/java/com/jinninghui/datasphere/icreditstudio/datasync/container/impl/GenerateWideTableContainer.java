package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.GenerateWideTable;

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
}
