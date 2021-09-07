package com.jinninghui.datasphere.icreditstudio.datasync.container;

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
