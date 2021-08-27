package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociatedType {
    /**
     * 标识【0：左关联，1：内关联，2：全关联】
     */
    private Integer code;

    /**
     * 关联关键字
     */
    private String keyword;
}
