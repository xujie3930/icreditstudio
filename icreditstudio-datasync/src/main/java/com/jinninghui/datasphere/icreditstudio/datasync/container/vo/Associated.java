package com.jinninghui.datasphere.icreditstudio.datasync.container.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class Associated {
    /**
     * 关联类型
     */
    private List<AssociatedType> assocTypes;
    /**
     * 关联条件
     */
    private List<String> assocConditions;
}
