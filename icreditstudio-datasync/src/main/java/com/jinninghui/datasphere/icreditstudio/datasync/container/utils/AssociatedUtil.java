package com.jinninghui.datasphere.icreditstudio.datasync.container.utils;

import com.jinninghui.datasphere.icreditstudio.datasync.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasync.container.Formatter;
import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.AssociatedDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.FormatterDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Peng
 */
public final class AssociatedUtil {

    public static Associated find(String dialect) {
        return AssociatedDialectKeyContainer.getInstance().find(dialect);
    }

    /**
     * 将可视化数据转化为查询sql
     *
     * @param vo
     * @return
     */
    public static String wideTableSql(AssociatedFormatterVo vo) {
        if (StringUtils.isBlank(vo.getDialect())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000061.getCode());
        }
        Formatter formatter = FormatterDialectKeyContainer.getInstance().find(vo.getDialect());
        if (Objects.isNull(formatter)) {
            throw new AppException("60000026");
        }
        return formatter.format(vo);
    }
}
