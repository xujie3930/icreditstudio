package org.apache.dolphinscheduler.api.param;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class DataxJson {
    private Integer customConfig;
    private DataxJsonContent content;
    private List<String> localParams;
}
