package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataSyncDialectSupportRequest {
    /**
     * 源类型
     */
    private Integer sourceType;
    /**
     * 数据库方言
     */
    private String dialect;
}
