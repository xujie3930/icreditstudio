package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class FileAssociated {

    /**
     * 方言
     */
    private String dialect;
    /**
     * 左表ID
     */
    private String leftSourceId;
    /**
     * 右表ID
     */
    private String rightSourceId;
    /**
     * 关联类型
     */
    private Integer associatedType;
    /**
     * 关联条件
     */
    private List<AssociatedCondition> conditions;
}
