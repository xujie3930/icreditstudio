package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class DialectAssociated {
    /**
     * 关联类型
     */
    private List<AssociatedType> associated;
    /**
     * 关联条件
     */
    private List<String> conditions;
}
