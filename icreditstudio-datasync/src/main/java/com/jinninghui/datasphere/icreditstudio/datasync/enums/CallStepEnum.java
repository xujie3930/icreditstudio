package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum CallStepEnum {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    ;
    private Integer code;

    public static CallStepEnum find(Integer code) {
        for (CallStepEnum value : CallStepEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
