package org.apache.dolphinscheduler.api.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionResult {
    private List<String> successNode;
    private List<String> failedNode;
}
