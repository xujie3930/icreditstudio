package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.result;

import lombok.Data;

/**
 * Created by PPai on 2021/6/18 13:17
 */
@Data
public class InfoNoticeTemp {
    /**
     * 接收人记录ID
     */
    private String recId;
    /**
     * 消息ID
     */
    private String infoId;
    /**
     * 接收人ID
     */
    private String receiverId;
    /**
     * 阅读时间
     */
    private String readTime;
    /**
     * 消息标题
     */
    private String infoTitle;
    /**
     * 消息内容
     */
    private String infoContent;
    /**
     * 消息类型
     */
    private String infoType;
    /**
     * 发送人ID
     */
    private String senderId;
    /**
     * 阅读状态
     */
    private String readStatus;
    /**
     * 创建时间
     */
    private Long createTime;
}
