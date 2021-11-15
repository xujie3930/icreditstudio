package org.apache.dolphinscheduler.api.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

@Data
public class LogPageRequest extends BusinessBasePageForm {

    private String taskId;
    private Integer taskStatus;
    private Long execTimeStart;
    private Long execTimeEnd;

}
