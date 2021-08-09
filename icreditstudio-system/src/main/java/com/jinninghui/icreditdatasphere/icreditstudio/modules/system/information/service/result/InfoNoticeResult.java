package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.result;

import lombok.Data;

@Data
public class InfoNoticeResult {

    private String id;

    private String infoTitle;

    private String infoContent;

    private String infoType;

    private String readStatus;

    private Long createTime;

    private String senderId;

    private String senderName;
}
