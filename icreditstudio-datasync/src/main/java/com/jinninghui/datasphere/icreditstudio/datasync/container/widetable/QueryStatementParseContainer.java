package com.jinninghui.datasphere.icreditstudio.datasync.container.widetable;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractMapContainer;

import java.util.Map;

/**
 * @author Peng
 */
public class QueryStatementParseContainer extends AbstractMapContainer<String, QueryStatementParseHandler> {

    private static QueryStatementParseContainer instance = new QueryStatementParseContainer();

    private QueryStatementParseContainer() {
    }

    public static QueryStatementParseContainer getInstance() {
        return instance;
    }

    public static QueryStatementParseHandler get(String sql) {
        QueryStatementParseHandler result = null;
        Map<String, QueryStatementParseHandler> container = QueryStatementParseContainer.getInstance().getContainer();
        for (Map.Entry<String, QueryStatementParseHandler> entry : container.entrySet()) {
            if (entry.getValue().isCurrentHandler(sql)) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }
}
