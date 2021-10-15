package com.jinninghui.datasphere.icreditstudio.datasync.service.time;

import lombok.Data;

import java.util.Date;

/**
 * @author Peng
 */
@Data
public class SyncTimeInterval {

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
}
