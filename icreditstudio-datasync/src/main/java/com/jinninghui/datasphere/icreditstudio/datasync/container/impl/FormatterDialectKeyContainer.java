package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractDialectTypeHandler;
import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;

/**
 * @author Peng
 */
public class FormatterDialectKeyContainer extends AbstractMapContainer<String, AbstractDialectTypeHandler> {
    private static FormatterDialectKeyContainer instance = new FormatterDialectKeyContainer();

    private FormatterDialectKeyContainer() {
    }

    public static FormatterDialectKeyContainer getInstance() {
        return instance;
    }
}
