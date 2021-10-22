package org.apache.dolphinscheduler.api.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.dolphinscheduler.common.process.Property;

import java.util.List;

/**
 * @author Peng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeParam {
    private int customConfig;
    private String json;
    private List<Property> localParams;
}
