package com.jinninghui.datasphere.icreditstudio.modules.system.information.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by PPai on 2021/6/18 15:45
 */
@Data
public class InformationDelAllUserInfoRequest {
    /**
     * 阅读状态(ALL Y N)
     */
    @NotBlank(message = "50009367")
    private String readStatus;
    @NotBlank(message = "50009366")
    private String infoType;
    @NotBlank(message = "50009365")
    private String userId;
}
