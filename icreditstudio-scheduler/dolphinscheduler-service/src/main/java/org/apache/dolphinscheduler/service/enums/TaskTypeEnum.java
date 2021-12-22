package org.apache.dolphinscheduler.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskTypeEnum {
    MANUAL("0"),
    CYCLE("1");

    private String code;

}
