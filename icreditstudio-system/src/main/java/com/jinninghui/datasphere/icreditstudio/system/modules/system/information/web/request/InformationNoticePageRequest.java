package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Created by PPai on 2021/6/17 14:48
 */
@Data
public class InformationNoticePageRequest extends BusinessBasePageForm {
    /**
     * 消息类型(S,N,W)
     */
    @NotBlank(message = "50009366")
    private String infoType;
    /**
     * 标题关键字
     */
    @Length(max = 200, message = "50009368")
    private String infoTitle;
    /**
     * 用户ID
     */
    @NotBlank(message = "50009365")
    private String userId;
    /**
     * 阅读状态(ALL,Y,N)
     */
    @NotBlank(message = "50009367")
    private String readStatus;
}
