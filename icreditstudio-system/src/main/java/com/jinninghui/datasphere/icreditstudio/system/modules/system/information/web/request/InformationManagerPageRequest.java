package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * @author 1
 */
@Data
public class InformationManagerPageRequest extends BusinessBasePageForm {
    /**
     * 消息标题
     */
    private String infoTitle;
    /**
     * 接收人名称
     */
    private String receiverName;
    /**
     * 消息类型
     */
    private String infoType;
    /**
     * 起始发送时间
     */
    private Long startTime;
    /**
     * 结束发送时间
     */
    private Long endTime;
}
