package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.web.request;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author 1
 */
@Data
public class InformationSendRequest {
    /**
     * 消息标题
     */
    @NotBlank(message = "50009370")
    @Length(max = 200, message = "50009368")
    private String infoTitle;
    /**
     * 消息内容
     */
    @Length(max = 2000, message = "50009369")
    private String infoContent;
    /**
     * InfoTypeEnum
     * 消息类型("S:系统消息,N:通知消息,W:预警消息")
     */
    @NotBlank(message = "50009366")
    private String infoType;
    /**
     * 发送人ID
     */
    @NotBlank(message = "50009371")
    private String senderId;
    /**
     * 接收者ID
     * 为空则发送给组织下所有用户
     */
    private Set<String> receiverIds;
}
