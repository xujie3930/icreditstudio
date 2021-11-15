package org.apache.dolphinscheduler.api.param;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

import java.util.Date;

@Data
public class LogPageParam extends BusinessBasePageForm {

    private String taskId;
    private Integer taskStatus;
    private Date execTimeStart;
    private Date execTimeEnd;

}
