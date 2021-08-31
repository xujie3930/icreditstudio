package com.jinninghui.datasphere.icreditstudio.datasync.container.utils;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AssociatedDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.AssociatedFormatter;
import com.jinninghui.datasphere.icreditstudio.datasync.container.FormatterDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;

/**
 * @author Peng
 */
public final class AssociatedUtil {

    public static Associated find(String dialect) {
        return AssociatedDialectKeyContainer.getInstance().find(dialect);
    }

    public static String wideTableSql(AssociatedFormatterVo vo) {
        AssociatedFormatter associatedFormatter = FormatterDialectKeyContainer.getInstance().find(vo.getDialect());
        return associatedFormatter.completion(vo);
    }
}
