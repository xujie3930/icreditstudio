package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service.result;

import lombok.Data;

/**
 * Created by PPai on 2021/6/18 18:37
 */
@Data
public class InformationInBoxResult {

    private String id;
    /**
     * 消息标题
     */
    private String infoTitle;
    /**
     * 消息内容
     */
    private String infoContent;
    /**
     * 发送人名称
     */
    private String senderName;
    /**
     * 发送时间
     */
    private Long sendTime;
    /**
     * 消息类型
     */
    private String infoType;
    /**
     * 接收人名称
     */
    private String receiverName;
    /**
     * 接收人ID
     */
    private String receiverId;
    /**
     * 阅读状态
     */
    private String readStatus;
    /**
     * 阅读时间
     */
    private String readTime;

}
