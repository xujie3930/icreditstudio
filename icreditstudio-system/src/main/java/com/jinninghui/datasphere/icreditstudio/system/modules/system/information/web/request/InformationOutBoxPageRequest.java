package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Created by PPai on 2021/6/18 18:11
 */
@Data
public class InformationOutBoxPageRequest extends BusinessBasePageForm {

    @NotBlank(message = "50009365")
    private String userId;
    /**
     * 消息标题
     */
    @Length(max = 200, message = "50009368")
    private String infoTitle;
    /**
     * 接收人姓名
     */
    @Length(max = 20, message = "50008003")
    private String receiverName;
    /**
     * 消息类型
     */
    private String infoType;

    private Long startTime;

    private Long endTime;
}
