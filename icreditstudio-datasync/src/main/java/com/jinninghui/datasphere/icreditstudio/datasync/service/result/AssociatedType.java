package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class AssociatedType {
    /**
     * 标识【0：左关联，1：内关联，2：全关联】
     */
    private Integer flag;
    /**
     * 数据库方言
     */
    private String dialect;
    /**
     * 关联关键字
     */
    private String content;
}
