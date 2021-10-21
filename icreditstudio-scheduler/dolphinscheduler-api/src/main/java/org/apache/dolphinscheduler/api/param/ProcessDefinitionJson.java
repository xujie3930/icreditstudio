package org.apache.dolphinscheduler.api.param;

import lombok.Data;
import org.apache.dolphinscheduler.common.process.Property;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class ProcessDefinitionJson {

    private List<Property> globalParams;
    /**
     * task list
     */
    private List<TaskNodeStruct> tasks;

    private int timeout;

    private String tenantCode;
}
