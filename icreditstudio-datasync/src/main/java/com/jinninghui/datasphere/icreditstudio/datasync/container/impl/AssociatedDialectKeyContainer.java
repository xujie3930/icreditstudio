package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;

/**
 * @author Peng
 */
public class AssociatedDialectKeyContainer extends AbstractMapContainer<String, Associated> {
    private static AssociatedDialectKeyContainer instance = new AssociatedDialectKeyContainer();

    private AssociatedDialectKeyContainer() {
    }

    public static AssociatedDialectKeyContainer getInstance() {
        return instance;
    }
}
