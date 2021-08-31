package com.jinninghui.datasphere.icreditstudio.datasync.container;

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
