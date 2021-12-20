package org.apache.dolphinscheduler.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskExecStatusEnum {
    SUCCESS(0),
    FAIL(1),
    RUNNING(2),
    WATTING(3);

    private Integer code;

}
