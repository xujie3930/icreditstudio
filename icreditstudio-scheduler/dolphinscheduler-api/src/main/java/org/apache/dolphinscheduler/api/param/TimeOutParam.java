package org.apache.dolphinscheduler.api.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeOutParam {
    private String strategy;
    private String interval;
    private boolean enable;
}
