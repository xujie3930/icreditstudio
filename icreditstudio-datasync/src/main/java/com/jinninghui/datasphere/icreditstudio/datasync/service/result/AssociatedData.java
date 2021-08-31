package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class AssociatedData {

    /**
     * 左表库
     */
    private String leftSourceDatabase;
    /**
     * 左表名称
     */
    private String leftSource;
    /**
     * 右表库
     */
    private String rightSourceDatabase;
    /**
     * 右表名称
     */
    private String rightSource;
    /**
     * 关联类型
     */
    private Integer associatedType;
    /**
     * 关联条件
     */
    private List<AssociatedCondition> conditions;
}
