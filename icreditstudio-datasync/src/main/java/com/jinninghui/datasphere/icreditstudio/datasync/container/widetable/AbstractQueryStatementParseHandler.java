package com.jinninghui.datasphere.icreditstudio.datasync.container.widetable;

/**
 * @author Peng
 */
public abstract class AbstractQueryStatementParseHandler implements QueryStatementParseHandler {

    public AbstractQueryStatementParseHandler() {
        register();
    }

    @Override
    public void register() {
        QueryStatementParseContainer.getInstance().put(getDialect(), this);
    }
}
