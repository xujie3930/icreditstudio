package org.apache.dolphinscheduler.api.param;

import cn.hutool.core.util.StrUtil;
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

    public String getStrategy() {
        if (StrUtil.isBlank(strategy)) {
            return null;
        }
        return strategy;
    }

    private String interval;
    private boolean enable;
}
