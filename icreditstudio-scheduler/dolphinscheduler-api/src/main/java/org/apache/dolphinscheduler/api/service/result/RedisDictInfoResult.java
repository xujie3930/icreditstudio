package org.apache.dolphinscheduler.api.service.result;

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
