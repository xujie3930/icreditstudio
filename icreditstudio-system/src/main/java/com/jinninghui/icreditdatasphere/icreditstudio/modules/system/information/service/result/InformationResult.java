package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.result;

import lombok.Data;

import java.util.Set;

/**
 * Created by PPai on 2021/6/17 14:44
 */
@Data
public class InformationResult {
    /**
     * 主键ID
     */
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
     * 发送人ID
     */
    private String senderId;
    /**
     * 发送人姓名
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
     * 接收人ID
     */
    private Set<String> receiverIds;
    /**
     * 接收人姓名
     */
    private Set<String> receiverNames;
}
