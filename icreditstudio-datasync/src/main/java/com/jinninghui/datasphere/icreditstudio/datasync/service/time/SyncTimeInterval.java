package com.jinninghui.datasphere.icreditstudio.datasync.service.time;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author Peng
 */
@Data
public class SyncTimeInterval {

    private String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 是否已执行
     */
    private boolean isExec;
    /**
     * 起始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 增量字段
     */
    private String incrementalField;
    /**
     * 时间格式化
     */
    private String timeFormat;

    public String formatStartTime() {
        if (startTime != null) {
            return DateUtil.format(startTime, TIME_FORMAT);
        }
        return null;
    }

    public String formatEndTime() {
        if (endTime != null) {
            return DateUtil.format(endTime, TIME_FORMAT);
        }
        return null;
    }
}
