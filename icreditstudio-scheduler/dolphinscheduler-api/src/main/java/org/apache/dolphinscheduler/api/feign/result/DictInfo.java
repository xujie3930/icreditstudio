package org.apache.dolphinscheduler.api.feign.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DictInfo {
    private String name;
    private String value;
    private String key;
}
