package com.jinninghui.datasphere.icreditstudio.datasync.container.vo;

import com.jinninghui.datasphere.icreditstudio.datasync.enums.AssociatedEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
public class AssociatedType {

    public AssociatedType(AssociatedEnum type, String keyword) {
        Objects.requireNonNull(type);
        this.type = type;
        this.keyword = keyword;
        this.code = type.getCode();
    }

    /**
     * 标识【0：左关联，1：内关联，2：全关联】
     */
    private AssociatedEnum type;

    private Integer code;
    /**
     * 关联关键字
     */
    private String keyword;
}
