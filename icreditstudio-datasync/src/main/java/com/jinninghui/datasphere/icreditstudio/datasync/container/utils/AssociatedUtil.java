package com.jinninghui.datasphere.icreditstudio.datasync.container.utils;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AssociatedDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;

/**
 * @author Peng
 */
public final class AssociatedUtil {

    public static Associated find(String dialect) {
        return AssociatedDialectKeyContainer.getInstance().find(dialect);
    }

    public static String wideTableSql() {
        return null;
    }
}
