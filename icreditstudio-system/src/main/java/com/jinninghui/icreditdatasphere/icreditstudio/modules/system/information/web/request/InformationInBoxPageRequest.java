package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.web.request;

import com.hashtech.businessframework.result.base.BusinessBasePageForm;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Created by PPai on 2021/6/18 18:37
 */
@Data
public class InformationInBoxPageRequest extends BusinessBasePageForm {

    @NotBlank(message = "50009365")
    private String userId;
    /**
     * 消息标题
     */
    @Length(max = 200, message = "50009368")
    private String infoTitle;
    /**
     * 消息接收人
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
