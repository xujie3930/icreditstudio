package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class AssociatedCondition {
    /**
     * 左字段
     */
    private String left;
    /**
     * 条件
     */
    private String associate;
    /**
     * 右字段
     */
    private String right;
}
