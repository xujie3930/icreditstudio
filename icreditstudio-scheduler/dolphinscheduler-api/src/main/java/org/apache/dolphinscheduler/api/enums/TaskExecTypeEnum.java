package org.apache.dolphinscheduler.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskExecTypeEnum {
    RE_RUN("0"),
    STOP("1");

    private String code;

}
