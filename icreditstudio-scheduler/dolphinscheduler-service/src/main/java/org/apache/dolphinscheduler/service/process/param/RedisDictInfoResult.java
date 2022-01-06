package org.apache.dolphinscheduler.service.process.param;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class RedisDictInfoResult {
    private String dictId;
    private String columnKey;
    private String columnValue;
}
