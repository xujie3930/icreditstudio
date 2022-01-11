package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Peng
 */
@Data
public class SyncCondition {
    /**
     * 增量字段
     */
    private String incrementalField;

    private String incrementalFieldLabel;

    public String getIncrementalFieldLabel() {
        if (StringUtils.isBlank(incrementalField)) {
            return "";
        }
        return StrUtil.subAfter(incrementalField, ".", true);
    }

    /**
     * 分区类型
     */
    private String partition;
    /**
     * 增量存储
     */
    private Boolean inc;
    /**
     * 数据源方言
     */
    private String dialect;

    public Boolean getInc() {
        if (inc == null) {
            return false;
        }
        return inc;
    }

    /**
     * 时间T+n
     */
    private Integer n;

    public Integer getN() {
        if (n == null || n <= 0) {
            return 1;
        }
        return n;
    }
}
