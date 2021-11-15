package org.apache.dolphinscheduler.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

import java.util.Date;

@Data
public class LogPageRequest extends BusinessBasePageForm {

    private String taskId;
    private Integer taskStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date execTimeStart;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date execTimeEnd;

}
