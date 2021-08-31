package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
