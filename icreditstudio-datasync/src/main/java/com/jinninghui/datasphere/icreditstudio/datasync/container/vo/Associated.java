package com.jinninghui.datasphere.icreditstudio.datasync.container.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.Optional;

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

    public String keyword(Integer code) {
        return Optional.ofNullable(assocTypes).orElse(Lists.newArrayList())
                .stream()
                .filter(associatedType -> associatedType.getCode().equals(code))
                .map(AssociatedType::getKeyword)
                .findFirst().orElse(null);
    }
}
